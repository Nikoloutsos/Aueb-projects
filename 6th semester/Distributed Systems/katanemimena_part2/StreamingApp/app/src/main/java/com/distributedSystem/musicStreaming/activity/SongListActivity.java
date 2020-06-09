package com.distributedSystem.musicStreaming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.adapter.SongListAdapter;
import com.distributedSystem.musicStreaming.databinding.ActivitySongListBinding;
import com.distributedSystem.musicStreaming.model.Artist;
import com.distributedSystem.musicStreaming.model.Song;
import com.distributedSystem.musicStreaming.viewmodel.SongListViewModel;

import org.json.JSONObject;

import java.util.List;

public class SongListActivity extends AppCompatActivity {

    ActivitySongListBinding binding;
    SongListViewModel viewModel;

    LiveData<List<Song>> onSongListChanged;
    LiveData<Artist> onArtistInfoChanged;
    LiveData<Boolean> onUnknownArtist;
    LiveData<Boolean> onErrorHappened;

    SongListAdapter songListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_song_list);


        viewModel = ViewModelProviders.of(this).get(SongListViewModel.class);
        viewModel.context = this;

        binding.songRv.setLayoutManager(new LinearLayoutManager(this));


        onSongListChanged = viewModel.getOnSongListChanged();
        onArtistInfoChanged = viewModel.getOnArtistInfoChanged();
        onErrorHappened = viewModel.getOnErrorHappened();
        onUnknownArtist = viewModel.getOnUnknownArtist();


        onSongListChanged.observe(this, new Observer<List<Song>>() {
                    @Override
                    public void onChanged(List<Song> songs) {
                        if (songs == null) return;
                        songListAdapter = new SongListAdapter(SongListActivity.this, songs);
                        binding.songRv.setAdapter(songListAdapter);
                        binding.loader.setVisibility(View.GONE);
                    }
        });
        onArtistInfoChanged.observe(this, new Observer<Artist>() {
            @Override
            public void onChanged(Artist artist) {
                if(artist == null)return;
                binding.artistName.setText(artist.name);
                Glide.with(SongListActivity.this)
                        .load(artist.urlPhoto)
                        .into(binding.artistPhoto);
                songListAdapter.artistUrl = artist.urlPhoto;
                songListAdapter.artistName = artist.name;
            }
        });
        onErrorHappened.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (!aBoolean)return;
                Toast.makeText(SongListActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });
        onUnknownArtist.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(!aBoolean)return;
                Toast.makeText(SongListActivity.this, "Unknown artist", Toast.LENGTH_SHORT).show();
            }
        });


        binding.backArrowImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        handleIntent();
        viewModel.fetchSongs();
    }

    private void handleIntent() {
        Intent intent = getIntent();
        if(intent == null) return;
        String artistName = intent.getStringExtra("artist_name");
        viewModel.setArtistName(artistName);
    }


}
