package com.distributedSystem.musicStreaming.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Song {
    public String name;
    public int durationInSeconds;

    public Song(String name, int durationInSeconds) {
        this.name = name;
        this.durationInSeconds = durationInSeconds;
    }

    /**
     * Used for converting the JSON response into POJO objects.
     * @param json JSON received as response for the request in BROKER
     * @return A list of songs
     */
    public static List<Song> parseAll(JSONObject json){
        try {
            ArrayList<Song> result = new ArrayList<>();
            JSONArray songs = json.getJSONArray("song_list");
            for (int i = 0; i < songs.length() ; i++) {
                JSONObject current = songs.getJSONObject(i);
                String name = current.getString("song_title");
                name = name.replace("_", " "); //Better visual representation
                int durationInSeconds = current.getInt("duration");
                result.add(new Song(name, durationInSeconds));
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }


    /**
     * Takes an integer representing seconds and converts the duration into this format -> 02:31, 00:11...
     */
    public String getDurationInHumanFormat(){
        StringBuilder sb = new StringBuilder();

        int minutes = durationInSeconds/60;
        int seconds = durationInSeconds%60;

        String formattedMinutes = minutes >= 10 ? ""+minutes : "0"+minutes;
        String formattedSeconds = seconds >= 10 ? ""+seconds : "0"+seconds;

        return formattedMinutes + ":" + formattedSeconds;
    }
}
