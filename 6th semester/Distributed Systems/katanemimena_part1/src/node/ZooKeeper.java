package node;

import org.json.JSONArray;
import org.json.JSONObject;
import util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

/**
 * Syncronize the whole distrubuted system
 */
public class ZooKeeper {
    volatile public List<Handler> handlerList = new ArrayList<>();
    public String syncInfo;
    private HashMap<String, Pair<String, Integer>> hashMap = new HashMap<>();
    private HashMap<String, String> artistToPhotoURL = new HashMap<>();
    List<Pair<String, Integer>> brokerAndHash;
    volatile boolean hasUserStartedProcess;
    volatile int numOfPublishers;
    volatile int numOfBrokers;


    public static void main(String[] args) throws Exception{
        ZooKeeper zooKeeper = new ZooKeeper();
        showIntroMsg();

        Server server = new Server();
        server.serverSocket = new ServerSocket(666);
        server.parent = zooKeeper;
        server.start();

        Scanner sc = new Scanner(System.in); // Waiting for user to start the process
        do{
            System.out.print(">> ");
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("start")){
                zooKeeper.countNodes();
                showStartMsg(zooKeeper.numOfPublishers, zooKeeper.numOfBrokers);
                zooKeeper.hasUserStartedProcess = true;
            }else{
                System.out.println("'"+s+"' is not recognized");
            }
        }while (!zooKeeper.hasUserStartedProcess);

        zooKeeper.computeHash();
        zooKeeper.broadcast();

    }

    /**
     * Broadcasts synced output into all registered brokers
     */
    private void broadcast(){
        for (Handler handler : handlerList) {
            if(handler.handshakeString.getString("identity").equalsIgnoreCase("broker")){
                handler.sendOutput();
            }
        }
    }

    /**
     * Show user-friendly messages
     */
    private static void showStartMsg(int numOfPublishers, int numOfBrokers) {
        System.out.println("==============================================================");
        System.out.println("|   ZOOKEPER IS STARTING                                     ");
        System.out.println("==============================================================");
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("| REGISTERED                                                 ");
        System.out.println("|                                                            ");
        System.out.println("| #Publisher ->"+ numOfPublishers);
        System.out.println("| #Broker ->" + numOfBrokers);
        System.out.println("==============================================================");
    }

    private static void showIntroMsg() {
        System.out.println("==============================================================");
        System.out.println("| ZOOKEPER INITIATED                                        ");
        System.out.println("==============================================================");
        System.out.println();
        System.out.println("==============================================================");
        System.out.println("| INFO                                                       ");
        System.out.println("|                                                            ");
        System.out.println("| STATUS -> Waiting for brokers and publishers to register   ");
        System.out.println("| NOTE -> Please type 'START' to start the distributed system");
        System.out.println("==============================================================");
    }

    /**
     * It counts how many publishers and brokers have been registered
     */
    private void countNodes(){

        for (Handler handler : handlerList) {
            if(handler.handshakeString == null) continue; // Handshakes that were not JSON or never arrived
            if (handler.handshakeString.getString("identity").equalsIgnoreCase("publisher")) {
                numOfPublishers++;
            }else if(handler.handshakeString.getString("identity").equalsIgnoreCase("broker")){
                numOfBrokers++;
            }
        }

    }

    /**
     * Accepting new connections
     */
    private static class Server extends Thread{
        ZooKeeper parent;
        ServerSocket serverSocket;

        @Override
        public void run() {

            try{
                while(true){
                    Socket socket = serverSocket.accept();
                    Handler handler = new Handler(socket, parent);
                    handler.start();
                    parent.handlerList.add(handler);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * The business logic of consistent hashing
     */
    private void computeHash(){
        //TODO use consistent algorithm here

        brokerAndHash = new ArrayList<>();
        List<String> brokers = new ArrayList<>();

        int l = Integer.parseInt(Util.BIG_PRIME_NUMBER);
        int noBrokers = 0;
        int noPublishers = 0;

        for (Handler handler : handlerList) {
            JSONObject json = handler.handshakeString;
            String identity = json.getString("identity");

            if (identity.equalsIgnoreCase("publisher")){
                noPublishers++;
                JSONArray artists = json.getJSONArray("artists");
                JSONArray photoURLs = json.getJSONArray("photoURLs");

                for (int i = 0; i < artists.length(); i++) {
                    String curArtist = artists.getString(i);
                    int hash = Util.getModMd5(curArtist);
                    String port = json.getString("connection_info");
                    hashMap.put(curArtist, new Pair<>(port, hash));
                    String curPhotoURL = photoURLs.getString(i);
                    artistToPhotoURL.put(curArtist, curPhotoURL);
                }

            }else if (identity.equalsIgnoreCase("broker")){
                noBrokers++;
                brokers.add(json.getString("connection_info"));
            }else if (identity.equalsIgnoreCase("consumer")){ // Evil consumer that tries to sneak at start

            }
        }

        int step = l/noBrokers;
        int index = 0;
        for (String broker : brokers) {
            brokerAndHash.add(new Pair<>(broker, index++ * step));
        }

        HashMap<String, String> artistToBroker = new HashMap<>();
        for (Map.Entry<String, Pair<String, Integer>> stringPairEntry : hashMap.entrySet()) {
            artistToBroker.put(stringPairEntry.getKey(), findBestBrokerForArtist(stringPairEntry.getKey()));
        }



        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();


        // CREATE SYNC DATA
        System.out.println(hashMap);
        for (Map.Entry<String, Pair<String, Integer>> stringPairEntry : hashMap.entrySet()) {

            JSONObject current = new JSONObject();

            current.put("name", stringPairEntry.getKey());
            current.put("photoURL", artistToPhotoURL.get(stringPairEntry.getKey()));
            current.put("publisher_connect_info", stringPairEntry.getValue().item1);
            current.put("broker_connect_info", findBestBrokerForArtist(stringPairEntry.getKey()));
            jsonArray.put(current);
        }

        jsonObject.put("output_consistent_hashing", jsonArray);
        jsonObject.put("System description", noPublishers + " publishers || " + noBrokers + " brokers");
        syncInfo = jsonObject.toString();
    }

    private String findBestBrokerForArtist(String artist){
        String bestBroker = brokerAndHash.get(0).item1;
        Integer artistHash = hashMap.get(artist).item2;
        for (Pair<String, Integer> andHash : brokerAndHash) {// for every broker
            if (andHash.item2 >= artistHash ){
                bestBroker = andHash.item1;
                break;
            }
        }
        return bestBroker;
    }

    /**
     * Handling a new connection
     */
    public static class Handler extends Thread {
        Socket socket;
        ZooKeeper zooKeeper;
        JSONObject handshakeString;

        public Handler(Socket socket, ZooKeeper zooKeeper){
            this.socket = socket;
            this.zooKeeper = zooKeeper;
        }

        @Override
        public void run() {
            try{
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                String registerRequest = in.readLine();
                Util.debug(registerRequest);
                handshakeString = new JSONObject(registerRequest);
                if(handshakeString.getString("identity").equalsIgnoreCase("consumer")){
                    out.println(zooKeeper.syncInfo.toString());
                }
                Util.debug("Someone just sent a handshake");
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("error handshake");
            }
        }

        public void sendOutput(){
            try {
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(zooKeeper.syncInfo);
            } catch (IOException e) {e.printStackTrace(); }
        }


    }

    public static class Pair<V1, V2> {
        public V1 item1;
        public V2 item2;

        public Pair(V1 item1, V2 item2) {
            this.item1 = item1;
            this.item2 = item2;
        }
    }
}
