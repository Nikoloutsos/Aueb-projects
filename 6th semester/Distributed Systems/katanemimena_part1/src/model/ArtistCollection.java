package model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtistCollection{
    public String artistName;
    public String description;
    public String urlPhoto;
    public String genre;
    public List<Song> songs;

    public ArtistCollection(String artistName, String description, String urlPhoto, String genre){
        this.artistName = artistName;
        this.description = description;
        this.urlPhoto = urlPhoto;
        this.genre = genre;
        this.songs = new ArrayList<>();
    }

    public void addSong(Song song){
        songs.add(song);
    }



    public JSONObject getAllSongs(){
        JSONObject jsonObject = new JSONObject();
        JSONArray array = new JSONArray();

        for (Song song : songs) {
            JSONObject curr = new JSONObject();
            curr.put("song_title", song.name);
            curr.put("duration", song.duration);

            array.put(curr);
        }

        jsonObject.put("photo_url", urlPhoto);
        jsonObject.put("artist", artistName);
        jsonObject.put("action", "SEARCH");
        jsonObject.put("song_list", array);

        jsonObject.put("status", "1");

        return jsonObject;
    }

}
