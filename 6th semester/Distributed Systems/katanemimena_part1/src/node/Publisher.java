package node;

import model.ArtistCollection;
import model.Song;
import org.json.JSONArray;
import org.json.JSONObject;
import util.Util;

import java.io.*;
import java.net.*;
import java.util.*;

public class Publisher {

    public  List<ArtistCollection> artistCollection =  new ArrayList<>();

    /**
     * Use this method to initiate a publisher
     */
    public static void main(String[] args) throws Exception{
        int port, noPublisher;
        try {
           port = Integer.parseInt(args[0]);
           noPublisher = Integer.parseInt(args[1]);
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            System.err.println("Args Input error");
           return;
        }

        Publisher publisher = new Publisher();
        publisher.artistCollection = Util.getArtistCollection(noPublisher);


        contactZooKeeper(publisher, port);

        ServerSocket s = new ServerSocket(port);
        while(true){
            Socket so = s.accept();
            Util.debug("A new broker was connected");
            Handler handler = new Handler(so, publisher);
            handler.start();
        }
    }


    public static void contactZooKeeper(Publisher publisher, int port) {
        JSONObject requestForZookeeper = getRequestForZookeeper(publisher, port);
        try {
            Socket socket = new Socket(Util.zookeeperConnectionInfo.item1, Util.zookeeperConnectionInfo.item2);
            PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
            pr.println(requestForZookeeper.toString());
        } catch (IOException e) {
            new Exception("");
        }
    }


    private static JSONObject getRequestForZookeeper(Publisher publisher, int port){
        String ip = Util.getIP();
        if(ip.equals("null")) new Exception("");
        JSONObject json = new JSONObject();
        JSONArray jArray = new JSONArray();
        JSONArray jArray1 = new JSONArray();
        for (ArtistCollection artistCollection : publisher.artistCollection) {
            jArray.put(artistCollection.artistName);
            jArray1.put(artistCollection.urlPhoto);
        }
        json.put("identity", "PUBLISHER");
        json.put("connection_info", ip + ":" + port);
        json.put("artists", jArray);
        json.put("photoURLs", jArray1);
        return json;
    }


    public static class Handler extends Thread {
        volatile Publisher publisher; // For getting the song file
        volatile Socket brokerSocket;
        volatile JSONObject request;

        public Handler(Socket brokerSocket, Publisher publisher){
            this.brokerSocket = brokerSocket;
            this.publisher = publisher;
        }


        @Override
        public void run() {

            // Get broker request
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(brokerSocket.getInputStream()));
                PrintWriter pr = new PrintWriter(brokerSocket.getOutputStream(), true);
                OutputStream out = brokerSocket.getOutputStream();
                String responseString = br.readLine(); // Wait here for request

                request = new JSONObject(responseString);

                String action = request.getString("action");
                if(action.equalsIgnoreCase("GET_MUSIC")){
                    String artist = request.getString("artist");
                    String songName = request.getString("song_name");

                    Song song = getSongByArtistAndSong(artist, songName);
                    ArtistCollection artistCollection = getArtistCollectionByArtist(artist);

                    if (song == null){
                        Util.debug("requested song  name not found");
                        pr.println(getNotFoundResponse().toString());
                        return;
                    }

                    pr.println(song.songDetails.toString()); // Send header message

                    byte[] songData = Util.loadSongFromDiskToRam(song);
                    List<byte[]> chunkedData = Util.chunkifyFile(songData);

                    for (byte[] chunk : chunkedData) {
                        out.write(chunk);
                        out.flush();
                    }

                    // RELEASE RESOURCES
                    out.close();
                    brokerSocket.close();

                }else if(action.equalsIgnoreCase("SEARCH")){ // Working
                    String artist = request.getString("artist");
                    Util.debug("A broker searched for " + artist);
                    ArtistCollection artistCollection = getArtistCollectionByArtist(artist); // todo error handling
                    JSONObject publisherResponse = artistCollection.getAllSongs();
                    pr.println(publisherResponse.toString());
                }

            } catch (Exception e) {e.printStackTrace();}
        }

        public ArtistCollection getArtistCollectionByArtist(String artist){
            ArtistCollection cur = null;
            for (ArtistCollection artistSong : publisher.artistCollection) {
                if (artistSong.artistName.equalsIgnoreCase(artist)){
                    cur = artistSong;
                    break;
                }
            }
            return cur;
        }

        public Song getSongByArtistAndSong(String artist, String songName){
            ArtistCollection cur = null;
            for (ArtistCollection artistAlbum : publisher.artistCollection) {
                if (artistAlbum.artistName.equalsIgnoreCase(artist)){
                    cur = artistAlbum;
                    break;
                }
            }

            Song curSong = null;
            for (Song song : cur.songs) {
                if(song.name.equalsIgnoreCase(songName)){
                    curSong = song;
                    break;
                }
            }
            return curSong;
        }


        private JSONObject getNotFoundResponse(){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status", "0");
            jsonObject.put("msg", "Requested song not found");
            return  jsonObject;
        }
    }
}
