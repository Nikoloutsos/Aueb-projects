package com.distributedSystem.musicStreaming.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.network.Constants;
import com.distributedSystem.musicStreaming.network.Request;
import com.distributedSystem.musicStreaming.util.OfflineStorage;
import com.distributedSystem.musicStreaming.util.SyncInfoManager;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SplashScreenViewModel extends ViewModel {

    public Context context;
    private MutableLiveData<Boolean> onErrorCommunicatingWithSever;
    private MutableLiveData<Boolean> onBadSyncInfoReceived;
    private MutableLiveData<Boolean> onSuccesfulZookeeperResponse;
    private OfflineStorage offlineStorage;

    public SplashScreenViewModel() {
        onBadSyncInfoReceived = new MutableLiveData<>();
        onErrorCommunicatingWithSever = new MutableLiveData<>();
        onSuccesfulZookeeperResponse = new MutableLiveData<>();
    }

    public void connectZookeeper(){
        try{
            new Thread( new Runnable() {
                @Override
                public void run() {
                    // IN BACKGROUND THREAD to avoid blocking the UI
                    try{
                        initializeFilesFolder();
                        // COMMUNICATES WITH ZOOKEEPER
                        SyncInfoManager syncInfoManager = SyncInfoManager.getInstance(context);
                        JSONObject response = syncInfoManager.updateSyncInfo();

                        // STORES ZOOKEEPER RESPONSE
                        offlineStorage = new OfflineStorage(context);
                        offlineStorage.setString(Constants.ZOOKEEPER_RESPONSE_CACHE, response.toString());

                        Log.d("aueb_debug", "zookeeper response=> " + response);

                        // NOTIFY UI
                        onSuccesfulZookeeperResponse.postValue(true);
                    }catch (Exception e){
                        onErrorCommunicatingWithSever.postValue(true);
                    }
            }} ).start();

        }catch (Exception e){
            onErrorCommunicatingWithSever.setValue(true);
        }
    }


    public void initializeFilesFolder(){
        try{
            FileOutputStream fos = context.openFileOutput("test" + ".readme", Context.MODE_PRIVATE);
            fos.write("Hi from the developers!".getBytes());
            fos.flush();
            fos.close();
        }catch (Exception e){

        }

    }

    /**
     * Getters for livedata
     */
    public LiveData<Boolean> getOnErrorCommunicatingWithSever() {
        return onErrorCommunicatingWithSever;
    }

    public LiveData<Boolean> getOnBadSyncInfoReceived() {
        return onBadSyncInfoReceived;
    }

    public LiveData<Boolean> getOnSuccesfulZookeeperResponse() {
        return onSuccesfulZookeeperResponse;
    }
}
