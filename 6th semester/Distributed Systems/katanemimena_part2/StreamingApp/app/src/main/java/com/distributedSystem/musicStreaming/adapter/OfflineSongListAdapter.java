package com.distributedSystem.musicStreaming.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.activity.OfflinePlaySongActivity;
import com.distributedSystem.musicStreaming.activity.PlaySongActivity;
import com.distributedSystem.musicStreaming.databinding.LayoutViewholderArtistListBinding;
import com.distributedSystem.musicStreaming.databinding.LayoutViewholderSongListBinding;
import com.distributedSystem.musicStreaming.model.ArtistConnectionInfo;
import com.distributedSystem.musicStreaming.model.Song;

import org.json.JSONObject;

import java.util.List;

public class OfflineSongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<JSONObject> data;
    public String artistUrl;
    public String artistName;


    public OfflineSongListAdapter(Context context, List<JSONObject> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_viewholder_song_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        try{
            final JSONObject song = data.get(position);
            ViewHolder rowCell = (ViewHolder) holder;

            String artist = song.getString("artist");
            String song_name = song.getString("song");

            rowCell.binding.songName.setText(song_name.replace("_", " "));
            rowCell.binding.duration.setText(getDurationInHumanFormat(321));
            rowCell.binding.songExtraInfo.setText(artist.replace("_", " "));


            rowCell.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent i = new Intent(context, OfflinePlaySongActivity.class);
                    i.putExtra("artist", song.getString("artist"));
                    i.putExtra("song", song.getString("song"));
                    context.startActivity(i);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });


        }catch (Exception e){}




    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * ViewHolder holds data for state of cell-row in recyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutViewholderSongListBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }


    /**
     * Takes an integer representing seconds and converts the duration into this format -> 02:31, 00:11...
     */
    private String getDurationInHumanFormat(int durationInSeconds){
        StringBuilder sb = new StringBuilder();

        int minutes = durationInSeconds/60;
        int seconds = durationInSeconds%60;

        String formattedMinutes = minutes >= 10 ? ""+minutes : "0"+minutes;
        String formattedSeconds = seconds >= 10 ? ""+seconds : "0"+seconds;

        return formattedMinutes + ":" + formattedSeconds;
    }
}
