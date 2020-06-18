package com.distributedSystem.musicStreaming.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.databinding.ActivityPlaySongBinding;
import com.distributedSystem.musicStreaming.util.MusicDownloaderManager;
import com.distributedSystem.musicStreaming.viewmodel.PlaySongViewModel;

import org.json.JSONException;
import org.json.JSONObject;

public class PlaySongActivity extends AppCompatActivity {
    ActivityPlaySongBinding binding;
    PlaySongViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_song);
        viewModel = ViewModelProviders.of(this).get(PlaySongViewModel.class);

        handleIntent();
        observeLiveData();
        setClickListeners();

        registerForContextMenu(binding.options3dots);
        viewModel.startBufferingSong();
    }

    /**
     * Receive incoming data from previous activity
     */
    void handleIntent(){
        viewModel.musicDownloaderManager = new MusicDownloaderManager(getApplicationContext());

        Intent parentIntent = getIntent();
        if(parentIntent == null) return;
        String artist_name = parentIntent.getStringExtra("artist_name");
        String artist_song = parentIntent.getStringExtra("song_name");
        String artist_url = parentIntent.getStringExtra("artist_url");

        Glide.with(this)
                .load(artist_url)
                .into(binding.aristPhotoOriginal);

        //Pass to viewmodel
        viewModel.artist = artist_name;
        viewModel.song = artist_song;


        //TODO DONT DO IT THIS WAY. CALL API FIRST AND THE UPDATE
        binding.songName.setText(artist_name.replace("_", " "));
        binding.songExtraInfo.setText(artist_song.replace("_", " "));
    }


    void observeLiveData(){
        viewModel.getOnErrorHappened().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    Toast.makeText(PlaySongActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        // https://lottiefiles.com/215-play-pause
        viewModel.getOnMusicPlayingStateChanged().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){//Music is playing
                    binding.loader.cancelAnimation();
                    binding.loader.setMinFrame(0);
                    binding.loader.setMaxFrame(30);
                    binding.loader.playAnimation();
                }else{//Music has stopped
                    binding.loader.cancelAnimation();
                    binding.loader.setMinFrame(30);
                    binding.loader.setMaxFrame(60);
                    binding.loader.playAnimation();
                }
            }
        });

        viewModel.getOnMetadataReceived().observe(this, new Observer<JSONObject>() {
            @Override
            public void onChanged(JSONObject jsonObject) {
                if(jsonObject == null) return;
                try {
                    Log.d("abc", "onChanged: -->" + jsonObject.getInt("duration"));
                    int duration = jsonObject.getInt("duration");
                    binding.seekBar.setMax(duration);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        viewModel.getOnProgressMusicChanged().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                Log.d("aws", "onChanged: " + integer);
                if (integer == null) return;
                binding.seekBar.setProgress(integer);
            }
        });
    }

    void setClickListeners(){

        binding.loader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.togglePlayer();
            }
        });
        binding.options3dots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.performLongClick();
            }
        });
        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                viewModel.seekMusic(seekBar.getProgress());
            }
        });

        binding.backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.extra_options_play_music_3dots_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.download_option:
                Toast.makeText(this, "Song saved to memory <3", Toast.LENGTH_SHORT).show();
                viewModel.saveMusic();
                break;
            case R.id.report_problem_option:
                Toast.makeText(this, "Ipsum lorem...", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
