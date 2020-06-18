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
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Responsible for managing distributed system info
 * Also has some handy methods
 */
public class SyncInfoManager {

    private static SyncInfoManager instance = null;
    private static OfflineStorage offlineStorage;


    private SyncInfoManager() {}

    public static SyncInfoManager getInstance(Context context) {
        if(instance == null) instance = new SyncInfoManager();
        offlineStorage = new OfflineStorage(context);
        return instance;
    }

    /**
     * @return Ip and port for communicating with appropriate broker for this certain artist.
     *         null otherwise
     */
    public Pair<String, Integer> findBrokerByArtist(String artist_name){
        try {
            // GET SAVED SYNC INFO
            String jsonString = offlineStorage.getString(Constants.ZOOKEEPER_RESPONSE_CACHE);
            JSONObject json = new JSONObject(jsonString);

            JSONArray array = json.getJSONArray("output_consistent_hashing");

            Pair<String, Integer> result = null;
            for (int i = 0; i < array.length() ; i++) {
                JSONObject current = array.getJSONObject(i);
                if(current.getString("name").equalsIgnoreCase(artist_name)){
                    String[] split = current.getString("publisher_connect_info").split(":");
                    result = new Pair<>(split[0], Integer.parseInt(split[1]));
                    break;
                }
            }

            return result;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Connects with Zookeeper and fetch latest syncInfo.
     * @return The JSON containing all syncInfo.
     */
    public JSONObject updateSyncInfo() throws Exception{
        // GET SYNC INFO FROM ZOOKEEPER
        Socket socket = new Socket(Constants.ZOOKEEPER_IP, Constants.ZOOKEEPER_PORT);
        PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        pr.println(Request.createZookeeperRequest());
        String response = br.readLine();
        JSONObject result = new JSONObject(response);
        return result;
    }


    
}
