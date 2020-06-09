package node;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Util;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Consumer{
   public static JSONObject syncInfo;

    public static void main(String[] args) throws Exception {
        Handler currentThread = new Handler();
        boolean firstThread = true;

        syncInfo = contactZooKeeper();
        System.out.println(syncInfo);
        showArtists();

        Scanner myObj = new Scanner(System.in);
        System.out.print("Would you like to play/search music(answer YES or NO) -> ");
        String keepGoing = myObj.nextLine();

        while (keepGoing.equalsIgnoreCase("yes")) {
            String requestString = getActionRequest();
            JSONObject jsonObject = new JSONObject(requestString);
            if (jsonObject.getInt("status") == 0){//not valid action or artist
                System.out.print("Would you like to keep playing/searching music(answer YES or NO) -> ");
                keepGoing = myObj.nextLine();
                continue;
            }

            JSONObject connectionInfo = getConnectionInfo(jsonObject.getString("artist"));

            Socket socket = new Socket(connectionInfo.getString("ip"), connectionInfo.getInt("port"));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(requestString);

            String responseString = in.readLine();
            JSONObject jResponse = new JSONObject(responseString);

            boolean getMusic = showResponse(jResponse);
            if (getMusic) {//wants to play a song
                InputStream inputStream = socket.getInputStream();
                Handler handler = new Handler(inputStream);
                if (firstThread) {
                    firstThread = false;
                } else {
                    currentThread.player.close();
                }
                handler.start();
                currentThread = handler;
            }
            System.out.print("Would you like to keep playing/searching music(answer YES or NO) -> ");
            keepGoing = myObj.nextLine();
        }

        if(currentThread.player != null) currentThread.player.close();
        System.out.println("Thank you for using our app. Hope you had a great experience!");


    }

    //----------------------------------------------------------------------------------------------------------------------
    public static JSONObject contactZooKeeper() throws Exception {
        // CREATE JSON
        JSONObject json = new JSONObject();
        json.put("identity", "CONSUMER");

        //SEND DATA
        Socket socket = new Socket(Util.zookeeperConnectionInfo.item1, Util.zookeeperConnectionInfo.item2);
        PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
        pr.println(json);

        //GET DATA
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        JSONObject result = new JSONObject(br.readLine());

       // System.out.println(result);
        return result;
    }

//----------------------------------------------------------------------------------------------------------------------

    /**
     * Prints artists
     */
    public static void showArtists() {
        List<String> artists = new ArrayList<>();

        JSONArray jArray = syncInfo.getJSONArray("output_consistent_hashing");
        for (int i = 0; i < jArray.length(); ++i) {
            JSONObject jObject = jArray.getJSONObject(i);
            artists.add(jObject.getString("name"));
        }

        System.out.println("---------------------------------------------");
        System.out.println("The available artists are the following:");
        for (String artist : artists) {
            System.out.println(artist);
        }
        System.out.println("---------------------------------------------");
    }

    //----------------------------------------------------------------------------------------------------------------------
/***
 * shows the response to the request made by the consumer.
 * returns true if the action requested is music and further actions need to be taken by the programm.
 * */
    public static boolean showResponse(JSONObject jResponse){
        if(jResponse.has("status") && jResponse.getString("status").equalsIgnoreCase("0")){
                System.out.println("The song you searched does not exits");
                System.out.println("---------------------------------------------");
                return false;
        }

        if(jResponse.getString("action").equalsIgnoreCase("SEARCH")){
            JSONArray jsonArray = jResponse.getJSONArray("song_list");
            if(jsonArray.length() == 0){
                System.out.println(jResponse.getString("artist") + "does not have any songs");
            }
            List<String> songs = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jObject = jsonArray.getJSONObject(i);
                songs.add(jObject.getString("song_title"));
            }

            System.out.println(jResponse.getString("artist") + "'s songs are the following:");
            for (String song : songs) {
                System.out.println(song);
            }

        }else{// get_music action requested
            System.out.println(jResponse.getString("artist") + "'s song \" " + jResponse.getString("song_name") +
                    "\" will start shortly");
            return true;
        }

        System.out.println("---------------------------------------------");
        return false;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static String getActionRequest() {
        String action = "";
        String artist = "";
        String songName = "";
        JSONObject jsonObject = new JSONObject();

        Scanner myObj = new Scanner(System.in);
        System.out.println("---------------------------------------------");
        System.out.println("Please enter request info:");
        System.out.print("action-(GET_MUSIC or SEARCH) -> ");
        action = myObj.nextLine();
        if (action.equalsIgnoreCase("GET_MUSIC") | action.equalsIgnoreCase("SEARCH")) {
            System.out.print("artist -> ");
            artist = myObj.nextLine();
            if(check_artist_exists(artist)){
                if(action.equalsIgnoreCase("GET_MUSIC")){
                    System.out.print("song name -> ");
                    songName = myObj.nextLine();
                }

                jsonObject.put("action", action);
                jsonObject.put("artist", artist);
                jsonObject.put("song_name", songName);
                jsonObject.put("status", 1);
            }else{
                System.out.println("Artist does not exist");
                jsonObject.put("status", 0);
            }
        }else{
            System.out.println("Ivalid action");
            jsonObject.put("status", 0);
        }

        System.out.println();

        return jsonObject.toString();

    }
    //----------------------------------------------------------------------------------------------------------------------
    public static boolean check_artist_exists(String artistRequested){
        List<String> artists = new ArrayList<>();
        JSONArray jArray = syncInfo.getJSONArray("output_consistent_hashing");
        for (int i = 0; i < jArray.length(); ++i) {
            JSONObject jObject = jArray.getJSONObject(i);
            if(jObject.getString("name").equalsIgnoreCase(artistRequested)){
                return true;
            }
        }
        return false;
    }
    //----------------------------------------------------------------------------------------------------------------------
    public static JSONObject getConnectionInfo(String requestedArtist) {
        JSONObject conInfo = new JSONObject();
        String info[] = new String[2];

        JSONArray jArray = syncInfo.getJSONArray("output_consistent_hashing");
        for (int i = 0; i < jArray.length(); ++i) {
            JSONObject jObject = jArray.getJSONObject(i);
            if (jObject.getString("name").equalsIgnoreCase(requestedArtist)) {
                info = jObject.getString("broker_connect_info").split(":");
                break;
            }
        }

        conInfo.put("ip", info[0]);
        conInfo.put("port", Integer.parseInt(info[1]));
        return conInfo;
    }
//----------------------------------------------------------------------------------------------------------------------



    public static class Handler extends Thread {
        Player player;

        public Handler(){}

        public Handler(InputStream inputStream) {
            try {
                player = new Player(inputStream);
                System.out.println("---------------------------------------------");
            } catch (JavaLayerException e) {
                System.out.println("Problem initiating player");
            }
        }

        @Override
        public void run() {
            try {
                player.play();
            } catch (JavaLayerException e) {
                System.out.println("Problem playing the song");
            }

        }

    }


}




//----------------------------------------------------------------------------------------------------------------------------------
/*
        List<Consumer> threads = new ArrayList<>();
        boolean firstThread = true;

        JSONObject syncInfo = contactZooKeeper();
        showArtists(syncInfo);

        Scanner myObj = new Scanner(System.in);
        System.out.println("Do you want to continue(answer YES or NO):");
        String keepGoing = myObj.nextLine();
        while (keepGoing.equalsIgnoreCase("yes")){

            String requestString = getActionRequest();
            JSONObject jsonObject = new JSONObject(requestString);
            JSONObject connectionInfo = getConnectionInfo(syncInfo, jsonObject.getString("artist"));

            Socket socket = new Socket(connectionInfo.getString("ip"), connectionInfo.getInt("port"));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println(requestString);

            String responseString = in.readLine();
            JSONObject jResponse = new JSONObject(responseString);

            if(jResponse.has("status")){//invalid action
                System.out.println(jResponse);
            } else if(jResponse.getString("action").equalsIgnoreCase("GET_MUSIC")){//wants to play a song
                System.out.println(jResponse);
                Consumer c  = new Consumer();
                c.inputStream = socket.getInputStream();
                c.p = new Player(c.inputStream);
                threads.add(c);
                if(firstThread){
                    firstThread = false;

                }else{
                    threads.get(0).p.close();
                    threads.remove(0);
                }
                c.start();
            }else{//searched for artist
                System.out.println(jResponse);
            }


            System.out.println("Do you want to continue(answer YES or NO):");
            keepGoing = myObj.nextLine();
        }

        threads.get(0).p.close();
        threads.remove(0);
        System.out.println("Thank you for using our app. Hope you had a great experience!");

 */
