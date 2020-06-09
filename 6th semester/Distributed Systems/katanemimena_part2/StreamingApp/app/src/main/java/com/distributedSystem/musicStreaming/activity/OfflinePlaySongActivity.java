package com.distributedSystem.musicStreaming.activity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.databinding.ActivityOfflinePlaySongBinding;
import com.distributedSystem.musicStreaming.util.MusicDownloaderManager;
import com.distributedSystem.musicStreaming.viewmodel.OfflinePlaySongViewModel;

import org.json.JSONObject;

import java.io.File;

public class OfflinePlaySongActivity extends AppCompatActivity {
    ActivityOfflinePlaySongBinding binding;
    OfflinePlaySongViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offline_play_song);
        viewModel = ViewModelProviders.of(this).get(OfflinePlaySongViewModel.class);

        handleIntent();
        setClickListeners();
        observeLiveData();

        viewModel.startBuffering();
    }

    private void observeLiveData() {
        viewModel.getOnMetadataReceived().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if(jsonObject == null) return;
                try{
                    int duration = jsonObject.getInt("duration");
                    binding.seekBar.setMax(duration);
                }catch (Exception e){}
            }
        });

        viewModel.getOnMusicTimeProgressChanged().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer progressInt) {
                if(progressInt == null) return;
                binding.seekBar.setProgress(progressInt);
            }
        });

        viewModel.getOnMusicPlayingStateChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean == null) return;
                if(aBoolean){//Music is playing
                    binding.playBtn.cancelAnimation();
                    binding.playBtn.setMinFrame(0);
                    binding.playBtn.setMaxFrame(30);
                    binding.playBtn.playAnimation();
                }else{//Music has stopped
                    binding.playBtn.cancelAnimation();
                    binding.playBtn.setMinFrame(30);
                    binding.playBtn.setMaxFrame(60);
                    binding.playBtn.playAnimation();
                }
            }
        });
    }

    private void setClickListeners() {
        binding.backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.seekMusicTo(seekBar.getProgress());
            }
        });

        binding.playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.toggle();
            }
        });
    }



    private void handleIntent(){
        viewModel.downloaderManager = new MusicDownloaderManager(getApplicationContext());
        viewModel.artist = getIntent().getExtras().getString("artist");
        viewModel.song = getIntent().getExtras().getString("song");

        //UPDATE UI
        binding.songName.setText(viewModel.song.replace("_", " "));
        binding.songExtraInfo.setText(viewModel.artist.replace("_", " "));
    }
}
