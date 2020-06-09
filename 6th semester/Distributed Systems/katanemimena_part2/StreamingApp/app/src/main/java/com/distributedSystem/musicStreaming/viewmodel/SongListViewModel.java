package com.distributedSystem.musicStreaming.viewmodel;

import android.content.Context;
import android.util.JsonReader;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.model.Artist;
import com.distributedSystem.musicStreaming.model.Song;
import com.distributedSystem.musicStreaming.network.Constants;
import com.distributedSystem.musicStreaming.network.Request;
import com.distributedSystem.musicStreaming.util.OfflineStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class SongListViewModel extends ViewModel {

    private MutableLiveData<Artist> onArtistInfoChanged;
    private MutableLiveData<List<Song>> onSongListChanged;
    private MutableLiveData<Boolean> onUnknownArtist;
    private MutableLiveData<Boolean> onErrorHappened;

    public Context context;
    private OfflineStorage offlineStorage;
    private String artistName;

    public SongListViewModel() {
        this.onSongListChanged = new MutableLiveData<>();
        this.onUnknownArtist = new MutableLiveData<>();
        this.onErrorHappened = new MutableLiveData<>();
        this.onArtistInfoChanged = new MutableLiveData<>();
    }


    public void fetchSongs(){
        if(artistName == null) return;

        new Thread( new Runnable() {
            @Override
            public void run() {
                // IN BACKGROUND THREAD to avoid blocking the UI
                Log.d("abc", "1");
                if(offlineStorage == null) offlineStorage = new OfflineStorage(context);

                Log.d("abc", "1");
                String string = offlineStorage.getString(Constants.ZOOKEEPER_RESPONSE_CACHE);
                try {
                    // FIND THE BROKER THAT IS RESPONSIBLE FOR CERTAIN ARTIST
                    JSONObject jsonObject = new JSONObject(string);
                    JSONArray array = jsonObject.getJSONArray("output_consistent_hashing");

                    JSONObject wanted = null;
                    for (int i = 0; i < array.length(); i++) { // LINEAR SEARCH
                        JSONObject current = array.getJSONObject(i);
                        if(current.getString("name").equalsIgnoreCase(artistName)){
                            wanted = current;
                            break;
                        }
                    }


                    Log.d("abc", "2");

                    if(wanted == null){
                        onUnknownArtist.postValue(true);
                        return;
                    }

                    Log.d("abc", "3");


                    // CONNECT WITH SOCKET
                    String[] connectionInfo = wanted.getString("broker_connect_info").split(":");
                    String ip = connectionInfo[0];
                    Integer port = Integer.parseInt(connectionInfo[1]);
                    Socket socket = new Socket(ip, port);
                    Log.d("abc", "4");

                    PrintWriter pr = new PrintWriter(socket.getOutputStream(), true);
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    JSONObject request = Request.createRequestSearchSongs(artistName);
                    Log.d("abc", "==>" + request + "");
                    pr.println(request);

                    String s = br.readLine();

                    Log.d("abc", s);
                    List<Song> songs = Song.parseAll(new JSONObject(s));
                    Artist artistInfo = Artist.parseIt(new JSONObject(s));

                    onSongListChanged.postValue(songs);
                    onArtistInfoChanged.postValue(artistInfo);

                } catch (Exception e) {
                    onErrorHappened.postValue(true);
                }

            }}).start();


    }

    public void setArtistName(String name){
        if(this.artistName != null) return;
        this.artistName = name;
    }

    public LiveData<List<Song>> getOnSongListChanged() {
        return onSongListChanged;
    }

    public LiveData<Boolean> getOnUnknownArtist() {
        return onUnknownArtist;
    }

    public LiveData<Artist> getOnArtistInfoChanged() {
        return onArtistInfoChanged;
    }

    public LiveData<Boolean> getOnErrorHappened() {
        return onErrorHappened;
    }
}
