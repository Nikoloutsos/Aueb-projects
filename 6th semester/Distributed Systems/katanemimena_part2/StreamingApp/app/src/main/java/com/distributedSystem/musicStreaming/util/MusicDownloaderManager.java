package com.distributedSystem.musicStreaming.util;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import com.distributedSystem.musicStreaming.network.Constants;
import com.distributedSystem.musicStreaming.network.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MusicDownloaderManager {
    Context context;
    OfflineStorage offlineStorage;

    public MusicDownloaderManager(Context context) {
        this.context = context;
        offlineStorage = new OfflineStorage(context);
    }

    public boolean isMusicSaved(String artist, String song){
        try {
            String fileName = artist + "_" + song + ".mp3";
            FileInputStream fis = context.openFileInput(fileName);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }

    public FileDescriptor downloadMusicFromServer(String artist, String song) throws IOException, JSONException {
        SyncInfoManager syncInfoManager = SyncInfoManager.getInstance(context);
        Pair<String, Integer> connectionInfo = syncInfoManager.findBrokerByArtist(artist);

        Socket socket = new Socket(connectionInfo.first, connectionInfo.second);
        PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pr.println(Request.createRequestGetMusic(artist, song));
        String response = br.readLine();
        JSONObject resp = new JSONObject(response);

        //READING BYTES
        int size_in_bytes = resp.getInt("size_in_bytes");
        DataInputStream dis = new DataInputStream(socket.getInputStream());
        byte[] songData = new byte[size_in_bytes];
        Log.d("aaa", "---downloadMusicFromServer: " + System.currentTimeMillis());
        dis.readFully(songData);
        Log.d("aaa", "---downloadMusicFromServer: " + System.currentTimeMillis());

        //CREATE A TEMP FILE
        saveMusic(songData, artist, song);
        return getMusicFromMemory(artist, song);
    }

    public boolean saveMusic(byte[] data, String artist, String song){
        if(!isMusicSaved(artist, song)){
            try{
                String string = offlineStorage.getString(Constants.SAVED_MUSIC_CACHE);
                if(string.isEmpty()) initializeSavedMusicCache();

                //Save file
                FileOutputStream fos = context.openFileOutput(artist + "_" + song + ".mp3", Context.MODE_PRIVATE);
                fos.write(data);
                fos.flush();
                fos.close();

                addSavedMusicInSharedPreferences(artist, song);
                return true;
            }catch (Exception e){}
        }
        return false;
    }

    public List<JSONObject> getListOfSavedMusic(){
        ArrayList<JSONObject> result = new ArrayList<>();

        try{
            String string = offlineStorage.getString(Constants.SAVED_MUSIC_CACHE);
            JSONObject jsonObject = new JSONObject(string);
            JSONArray data = jsonObject.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                result.add(data.getJSONObject(i));
            }
        }catch (Exception e){}

        return result;
    }

    public void deleteSavedMusic(String author, String song){
        String fileName = author + "_"  + song + ".mp3";
        File file = new File("/data/data/com.distributedSystem.musicStreaming/files/" + fileName);
        if(file.exists())
            file.delete();
    }

    public FileDescriptor getMusicFromMemory(String artist, String song){
        String title = artist + "_" + song + ".mp3";
        FileDescriptor fd = null;
        try {
            FileInputStream fileInputStream = context.openFileInput(title);
            fd = fileInputStream.getFD();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fd;
    }

    private void initializeSavedMusicCache(){
        offlineStorage.setString(Constants.SAVED_MUSIC_CACHE, "{ \"data\": [] }");
    }

    private void addSavedMusicInSharedPreferences(String artist, String song) {
        try {
            String string = offlineStorage.getString(Constants.SAVED_MUSIC_CACHE);
            JSONObject json = new JSONObject(string);

            JSONObject item = new JSONObject();
            item.put("artist", artist);
            item.put("song", song);

            JSONArray data = json.getJSONArray("data");
            data.put(item);
            JSONObject newJson = new JSONObject();
            newJson.put("data", data);
            offlineStorage.setString(Constants.SAVED_MUSIC_CACHE, newJson.toString());
        } catch (Exception e) {
        }
    }

}
