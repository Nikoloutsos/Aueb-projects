package com.distributedSystem.musicStreaming.viewmodel;

import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.util.MusicDownloaderManager;

import org.json.JSONObject;

import java.io.FileDescriptor;

public class PlaySongViewModel extends ViewModel {

    private MutableLiveData<Boolean> onMusicPlayingStateChanged = new MutableLiveData<>();
    private MutableLiveData<Boolean> onErrorHappened = new MutableLiveData<>();
    private MutableLiveData<Integer> onProgessMusicChanged = new MutableLiveData<>();
    private MutableLiveData<JSONObject> onMetadataReceived = new MutableLiveData<>();

    private MediaPlayer mediaPlayer = new MediaPlayer();
    public MusicDownloaderManager musicDownloaderManager;
    boolean isPlaying = false;
    private boolean musicWasSavedForOffline;


    public String artist;
    public String song;

    public void togglePlayer(){
        isPlaying = !isPlaying;
        onMusicPlayingStateChanged.setValue(isPlaying);
        if(isPlaying){
            playMusic();
        }else{
            stopMusic();
        }
    }


    /**
     * Starts downloading the song
     * Gets mp3 bytes for playing the audio
     */
    public void startBufferingSong(){
        if(artist == null || song == null) onErrorHappened.setValue(true);

        new Thread( new Runnable() {
            @Override
            public void run() {
                // IN BACKGROUND THREAD to avoid blocking the UI
                try{
                    musicWasSavedForOffline = musicDownloaderManager.isMusicSaved(artist, song);
                    FileDescriptor music = musicDownloaderManager.downloadMusicFromServer(artist, song);
                    mediaPlayer.setDataSource(music);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    pushMetadataToUI();
                    isPlaying = true;
                    onMusicPlayingStateChanged.postValue(true);
                }catch (Exception e){
                    onErrorHappened.postValue(true);
                }
            }
        }).start();

        enableOnMusicTimeChangedObserver();
    }

    public void playMusic(){
        mediaPlayer.start();
    }

    public void stopMusic() {
        mediaPlayer.pause();
    }

    public void seekMusic(int ms){
        mediaPlayer.seekTo(ms);
    }

    public void saveMusic(){
        musicWasSavedForOffline = true;
    }

    private void enableOnMusicTimeChangedObserver(){
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!= null){
                    Log.d("abc", "run: " + mediaPlayer.getCurrentPosition());
                    onProgessMusicChanged.setValue(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }

            }
        });
    }

    private void pushMetadataToUI(){
        try{
            Log.d("abc", "pushMetadataToUI: ");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("duration", mediaPlayer.getDuration());
            onMetadataReceived.postValue(jsonObject);
        }catch (Exception e){}
    }


    /**
     * Expose Livedata in UI
     */
    public LiveData<Boolean> getOnMusicPlayingStateChanged() {
        return onMusicPlayingStateChanged;
    }

    public LiveData<Boolean> getOnErrorHappened() {
        return onErrorHappened;
    }

    public LiveData<Integer> getOnProgressMusicChanged() {
        return onProgessMusicChanged;
    }

    public LiveData<JSONObject> getOnMetadataReceived() {
        return onMetadataReceived;
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        if(!musicWasSavedForOffline) musicDownloaderManager.deleteSavedMusic(artist, song);
    }
}
