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
import com.distributedSystem.musicStreaming.activity.PlaySongActivity;
import com.distributedSystem.musicStreaming.databinding.LayoutViewholderArtistListBinding;
import com.distributedSystem.musicStreaming.databinding.LayoutViewholderSongListBinding;
import com.distributedSystem.musicStreaming.model.ArtistConnectionInfo;
import com.distributedSystem.musicStreaming.model.Song;

import java.util.List;

public class SongListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    Context context;
    List<Song> data;
    public String artistUrl;
    public String artistName;


    public SongListAdapter(Context context, List<Song> data) {
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
        final Song song = data.get(position);
        ViewHolder rowCell = (ViewHolder) holder;


        rowCell.binding.songName.setText(song.name);
        rowCell.binding.duration.setText(song.getDurationInHumanFormat());
        rowCell.binding.songExtraInfo.setText("{artist_name} - {genre}");

        rowCell.binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, PlaySongActivity.class);
                i.putExtra("artist_name", artistName);
                i.putExtra("song_name", song.name.replace(" ", "_"));
                i.putExtra("artist_url", artistUrl);
                context.startActivity(i);
            }
        });

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
}
