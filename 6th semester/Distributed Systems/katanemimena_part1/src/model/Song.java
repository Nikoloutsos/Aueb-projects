package model;

import org.json.JSONObject;
import util.Util;

public class Song {

    private ArtistCollection owner;
    public String name;
    public String os_location;
    public String urlPhoto;
    public int duration;
    public JSONObject songDetails;


    public Song(ArtistCollection owner, String name, String os_location, String urlPhoto, int duration) {
        this.name = name;
        this.os_location = os_location;
        this.urlPhoto = urlPhoto;
        this.duration = duration;
        this.owner = owner;

        songDetails = new JSONObject();
        songDetails.put("action","GET_MUSIC");
        songDetails.put("photo_url", urlPhoto);
        songDetails.put("artist", owner.artistName);
        songDetails.put("song_name",name);
        songDetails.put("duration", duration);
        songDetails.put("size_in_bytes", Util.getFileSizeBytes(this));
    }

    public byte[] loadSong(){
        return Util.loadSongFromDiskToRam(this);
    }

}
