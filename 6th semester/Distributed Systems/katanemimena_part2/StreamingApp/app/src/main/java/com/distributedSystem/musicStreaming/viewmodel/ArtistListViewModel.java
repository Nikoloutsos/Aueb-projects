package com.distributedSystem.musicStreaming.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.model.ArtistConnectionInfo;
import com.distributedSystem.musicStreaming.network.Constants;
import com.distributedSystem.musicStreaming.util.OfflineStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ArtistListViewModel extends ViewModel {
    private OfflineStorage offlineStorage;
    private MutableLiveData<List<ArtistConnectionInfo>> onArtistList;
    public Context context;

    public ArtistListViewModel(){
        onArtistList = new MutableLiveData<>();
    }

    /**
     * Get artists from zookeeper response and pass it to UI from a background thread
     */
    public void fetchArtists(){
        if(offlineStorage==null) offlineStorage = offlineStorage = new OfflineStorage(context);
        try{
            new Thread( new Runnable() {
                @Override
                public void run() {
                    // IN BACKGROUND THREAD to avoid blocking the UI
                    try{
                        Thread.sleep(1500); // Simulate delay
                        String cached = offlineStorage.getString(Constants.ZOOKEEPER_RESPONSE_CACHE);
                        onArtistList.postValue(parseResponse(cached));
                    }catch (Exception e){

                    }
                }} ).start();

        }catch (Exception e){

        }
    }

    /**
     * Parse zookeeper response and converts it into pojo
     */
    List<ArtistConnectionInfo> parseResponse(String cached){
        if(cached.isEmpty()) return null;
        ArrayList<ArtistConnectionInfo> result = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(cached);
            JSONArray output_consistent_hasing = json.getJSONArray("output_consistent_hashing");
            for (int i = 0; i < output_consistent_hasing.length(); i++) {
                JSONObject current = output_consistent_hasing.getJSONObject(i);
                result.add(ArtistConnectionInfo.parseIt(current));
            }

        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

        return result;

    }


    public LiveData<List<ArtistConnectionInfo>> getOnArtistList() {
        return onArtistList;
    }
}

