package com.distributedSystem.musicStreaming.model;

import org.json.JSONException;
import org.json.JSONObject;

public class Artist {
    public String name;
    public String urlPhoto;

    public Artist(String name, String urlPhoto) {
        this.name = name;
        this.urlPhoto = urlPhoto;
    }

    public static Artist parseIt(JSONObject jsonObject){
        try {
            String name = jsonObject.getString("artist");
            String urlPhoto = jsonObject.getString("photo_url");

            return new Artist(name, urlPhoto);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
