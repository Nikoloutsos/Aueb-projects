package com.distributedSystem.musicStreaming.viewmodel;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.util.MusicDownloaderManager;

import org.json.JSONObject;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class OfflinePlaySongViewModel extends ViewModel {

    private MutableLiveData<JSONObject> onMetadataReceived = new MutableLiveData<>();
    private MutableLiveData<Integer> onMusicTimeProgressChanged = new MutableLiveData<>();
    private MutableLiveData<Boolean> onMusicPlayingStateChanged = new MutableLiveData<>();

    public String artist;
    public String song;
    public MusicDownloaderManager downloaderManager;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private boolean isPlaying;



    public void toggle(){
        isPlaying = !isPlaying;
        if(isPlaying){
            mediaPlayer.start();
            onMusicPlayingStateChanged.setValue(true);
        }else{
            mediaPlayer.pause();
            onMusicPlayingStateChanged.setValue(false);
        }
    }

    public void startBuffering(){
        try{
            FileDescriptor musicFromMemory = downloaderManager.getMusicFromMemory(artist, song);
            mediaPlayer.setDataSource(musicFromMemory);
            mediaPlayer.prepare();
            mediaPlayer.start();
            pushMetadataToUI();
            enableOnMusicTimeChangedObserver();
            toggle();
        }catch (IOException e){}
    }

    public void seekMusicTo(int ms){
        mediaPlayer.seekTo(ms);
    }

    private void enableOnMusicTimeChangedObserver(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer != null){
                    Log.d("aws", "tic time " + mediaPlayer.getCurrentPosition());
                    onMusicTimeProgressChanged.setValue(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        });
    }

    private void pushMetadataToUI(){
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("duration", mediaPlayer.getDuration());
            onMetadataReceived.setValue(jsonObject);
        }catch (Exception e){}
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public LiveData<JSONObject> getOnMetadataReceived() {
        return onMetadataReceived;
    }

    public LiveData<Integer> getOnMusicTimeProgressChanged() {
        return onMusicTimeProgressChanged;
    }

    public LiveData<Boolean> getOnMusicPlayingStateChanged() {
        return onMusicPlayingStateChanged;
    }
}
