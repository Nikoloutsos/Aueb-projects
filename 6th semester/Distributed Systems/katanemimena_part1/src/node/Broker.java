package node;

import org.json.JSONException;
import org.json.JSONObject;
import util.Util;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Broker {


    private volatile static JSONObject consistentHashingInfo;

    public static void main(String[] args) throws Exception{
        int port = Integer.parseInt(args[0]);
        ServerSocket s = new ServerSocket(port);

        // BLOCK UNTIL SYNC
        contactZooKeeper(""+port);

        // Mock zookeper response
//        String mock = "{\n" +
//                "\t\"System description\" : \"4 publishers | 2 brokers\",\n" +
//                "\t\"output_consistent_hasing\" : [\n" +
//                "\t\t{\n" +
//                "\t\t\t\"name\" : \"adele\",\n" +
//                "\t\t\t\"publisher_connect_info\" : \"192.168.1.7:3000\",\n" +
//                "\t\t\t\"broker_connect_info\": \"192.168.1.7:4000\"\n" +
//                "\t\t},\n" +
//                "\t\t{\n" +
//                "\t\t\t\"name\" : \"Justin bieber\",\n" +
//                "\t\t\t\"publisher_connect_info\" : \"192.168.1.7:3000\",\n" +
//                "\t\t\t\"broker_connect_info\": \"192.168.1.7:4001\"\n" +
//                "\t\t}\n" +
//                "\t]\n" +
//                "}".replaceAll("\n", "").replaceAll("\t", "");

//        consistentHashingInfo = new JSONObject(mock);
        while(true){
            Socket so = s.accept();
            Handler handler = new Handler(so);
            handler.start();
            Util.debug("A new client was connected");
        }
    }

    public static void contactZooKeeper(String port) throws Exception {

        String ip = Util.getIP();
        if(ip.equals("null")) throw new UnknownHostException("Could not get ip from machine");

        // CREATE JSON
        String connectionInfo = ip + ":" + port;

        JSONObject json = new JSONObject();
        json.put("identity", "BROKER");
        json.put("connection_info", connectionInfo);

        //SEND DATA
        Socket socket = new Socket( Util.zookeeperConnectionInfo.item1, Util.zookeeperConnectionInfo.item2);
        PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
        pr.println(json);

        Util.debug("Broker " + connectionInfo + " is connecting with Zookeeper");

        //GET DATA
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        consistentHashingInfo = new JSONObject(br.readLine());
    }

    public static class Handler extends Thread {
        volatile Socket consumerSocket;
        volatile Socket publisherSocket;
        PublisherHandler ph;
        ConsumerHandler ch;
        volatile JSONObject request; // Watched from Publisher
        volatile JSONObject publisherResponse; // Watched from consumerHandler
        volatile boolean isMalformedRequest;


        public Handler(Socket consumerSocket){
            this.consumerSocket = consumerSocket;
        }


        @Override
        public void run() {
             ch = new ConsumerHandler(this);
             ph = new PublisherHandler(this);
             ch.start();
             ph.start();

            try {
                ch.join();
                ph.join();
            } catch (InterruptedException e) { e.printStackTrace();}

        }

    }


    public static class PublisherHandler extends Thread{
        Handler parent;

        public PublisherHandler(Handler parent){
            this.parent = parent;
        }

        @Override
        public void run() {

            while (parent.request == null){ // Wait for a request
                if (parent.isMalformedRequest){
                    Util.debug("stoping publisher handler");
                    return; // Stop if customer sent bad request
                }
            }

            // Find publisher
            JSONObject responseInfo = new JSONObject();
            JSONObject connectionInfo = getPublisherByArtist(parent.request.getString("artist"));

            // Unknown artist
            if (connectionInfo == null) {
                responseInfo.put("status", "0");
                responseInfo.put("msg", "unknown artist " + parent.request.getString("artist"));
                parent.publisherResponse = responseInfo;
                return;
            }

            // Push request to publisher
            String[] conInfo = connectionInfo.getString("publisher_connect_info").split(":");
            try {
                Socket socket = new Socket(conInfo[0], Integer.parseInt(conInfo[1]));
                parent.publisherSocket = socket;


                PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pr.println(parent.request.toString());

                String publisherHeader = br.readLine();
                Util.debug(publisherHeader);
                JSONObject jsonHeader = new JSONObject(publisherHeader);
                parent.publisherResponse = jsonHeader;

            } catch (Exception e) {e.printStackTrace();}
        }

        private JSONObject getPublisherByArtist(String artist){
            JSONObject searchedData = null;

            for (Object output_consistent_hashing : consistentHashingInfo.getJSONArray("output_consistent_hashing")) {
                JSONObject current = (JSONObject)output_consistent_hashing;

                if (current.getString("name").equalsIgnoreCase(artist)){
                    searchedData = current;
                    break;
                }
            }

            return searchedData;
        }
    }

    public static class ConsumerHandler extends Thread{
        Handler parent;
        BufferedReader bf;
        DataOutputStream dos;
        PrintWriter pr;
        InputStream in;
        OutputStream out;

        public ConsumerHandler(Handler parent){
            this.parent = parent;
            try {
                bf = new BufferedReader(new InputStreamReader(parent.consumerSocket.getInputStream()));
                pr = new PrintWriter(parent.consumerSocket.getOutputStream(), true);
                dos = new DataOutputStream(parent.consumerSocket.getOutputStream());
                in = parent.consumerSocket.getInputStream();
                out = parent.consumerSocket.getOutputStream();
            } catch (IOException e) {e.printStackTrace();}

        }

        @Override
        public void run() {

            try {
                String s = bf.readLine();
                System.out.println(s);
                JSONObject request = new JSONObject(s);
                if(!checkRequest(request)){
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", "0");
                    jsonObject.put("msg", "Bad request");
                    pr.println(jsonObject);
                    parent.isMalformedRequest = true;
                    return;
                } //Checks if request is okay
                parent.request = request;

                while(parent.publisherResponse == null){ // Wait for publisher response

                }

                pr.println(parent.publisherResponse.toString()); // Send publisher response to consumer
                Util.debug("Start sending publisher response to consumer");

                int sizeInBytes = 0;
                try{
                    sizeInBytes = parent.publisherResponse.getInt("size_in_bytes");
                }catch (JSONException e){}


                if (sizeInBytes > 0){ // if publisher is sending  music
                    int totalBytesCount = 0;
                    int len = 1024*16; // chunk size

                    byte[] buffer = new byte[len];
                    DataInputStream dis = new DataInputStream(parent.publisherSocket.getInputStream());


                   while (true){
                       if(sizeInBytes - totalBytesCount < len){
                           len = sizeInBytes - totalBytesCount;
                           buffer = new byte[len];
                       }

                       dis.readFully(buffer);
                       totalBytesCount += len;

                       out.write(buffer);
                       out.flush();
                       if(sizeInBytes == totalBytesCount) break;
                   }
                   Util.debug("Finish with sending the file");

                }

            } catch (IOException e) { e.printStackTrace(); }
            catch (JSONException e){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "0");
                jsonObject.put("msg", "Request is not in JSON format");
                pr.println(jsonObject);
            }
            finally { // Releasing resources
                try {
                    out.close();
                    if (parent.consumerSocket != null) parent.consumerSocket.close();
                    if (parent.publisherSocket != null) parent.publisherSocket.close();
                } catch (IOException e) {e.printStackTrace();}
            }

        }

        private boolean checkRequest(JSONObject j) {
            try{
                String action = j.getString("action");
                boolean isSearch = action.equalsIgnoreCase("search");
                boolean isMusic = action.equalsIgnoreCase("get_music");
                j.getString("artist"); // It will throw exception if omitted
                if (isMusic){
                    j.getString("song_name");
                }
                if(isMusic || isSearch){

                }else{
                    System.out.println("111111");
                    return false;
                }
                return true;
            }catch (Exception e){
                System.out.println("22222");
                return false;
            }
        }
    }






}
