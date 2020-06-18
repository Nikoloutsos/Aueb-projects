package com.distributedSystem.musicStreaming.network;

import org.json.JSONException;
import org.json.JSONObject;

public class Request {

    public static final String ACTION_GET_MUSIC= "GET_MUSIC";
    public static final String ACTION_SEARCH = "SEARCH";
    public static final String IDENTITY_CONSUMER = "CONSUMER";



    public static JSONObject createZookeeperRequest(){
        try {
            JSONObject result = new JSONObject();
            result.put("identity", "CONSUMER");

            return result;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Creates a JSON request that will be sent to broker
     */
    public static JSONObject createRequestSearchSongs(String artistName){
        try{
            JSONObject request = new JSONObject();
            request.put("identity", IDENTITY_CONSUMER);
            request.put("action", ACTION_SEARCH);
            request.put("artist", artistName);
            return request;

        }catch (Exception e){
            return null;
        }
    }

    /**
     * Creates a JSON request that will be sent to broker
     */
    public static JSONObject createRequestGetMusic(String artistName, String songName){
        try{
            JSONObject request = new JSONObject();
            request.put("identity", IDENTITY_CONSUMER);
            request.put("action", ACTION_GET_MUSIC);
            request.put("artist", artistName);
            request.put("song_name", songName);
            return request;

        }catch (Exception e){
            return null;
        }
    }



}
