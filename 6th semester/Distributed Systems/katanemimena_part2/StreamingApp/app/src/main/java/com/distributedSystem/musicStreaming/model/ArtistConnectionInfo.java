package com.distributedSystem.musicStreaming.model;

import android.util.Pair;

import org.json.JSONException;
import org.json.JSONObject;

public class ArtistConnectionInfo {
    public String artistName;
    public String urlPhoto;
    public Pair<String, Integer> publisherConnectionInfo;
    public Pair<String, Integer> brokerConnectionInfo;

    private ArtistConnectionInfo(String artistName, Pair<String, Integer> publisherConnectionInfo, Pair<String, Integer> brokerConnectionInfo, String urlPhoto) {
        this.artistName = artistName;
        this.publisherConnectionInfo = publisherConnectionInfo;
        this.brokerConnectionInfo = brokerConnectionInfo;
        this.urlPhoto = urlPhoto;
    }

    /**
     * @param json jsonObject containing essential data
     */
    public static ArtistConnectionInfo parseIt(JSONObject json){
        try{
            String artist_name = json.getString("name");
            String publisher_connect_info = json.getString("publisher_connect_info");
            String broker_connect_info = json.getString("broker_connect_info");
            String url_photo = json.getString("photoURL");

            String[] publisher_connect_info_split = publisher_connect_info.split(":");
            String[] broker_connect_info_split = broker_connect_info.split(":");

            return new ArtistConnectionInfo(artist_name,
                              new Pair<>(publisher_connect_info_split[0], Integer.parseInt(publisher_connect_info_split[1])),
                              new Pair<>(broker_connect_info_split[0], Integer.parseInt(broker_connect_info_split[1])),
                                url_photo);
        }catch (JSONException e){
            return null;
        }
    }



}
