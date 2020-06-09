package com.distributedSystem.musicStreaming.adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.activity.SongListActivity;
import com.distributedSystem.musicStreaming.databinding.LayoutViewholderArtistListBinding;
import com.distributedSystem.musicStreaming.model.ArtistConnectionInfo;

import java.util.List;

/**
 * Used for populating the recycler view in ArtistListFragment
 * @see com.distributedSystem.musicStreaming.fragment.ArtistListFragment
 */
public class ArtistListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<ArtistConnectionInfo> data;
    Context context;

    public ArtistListAdapter(List<ArtistConnectionInfo> data, Context context) {
        this.data = data;
        this.context = context;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_viewholder_artist_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        final ArtistConnectionInfo artistConnectionInfo = data.get(position);
        ViewHolder rowCell = (ViewHolder) holder;

        /// Configure rowCell
        rowCell.binding.artistName.setText(artistConnectionInfo.artistName);

        Glide.with(context)
                .load(artistConnectionInfo.urlPhoto)
                .into(rowCell.binding.artistPhoto);

        rowCell.binding.rootLayoutCl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, SongListActivity.class);
                i.putExtra("artist_name", artistConnectionInfo.artistName);

                context.startActivity(i);
            }
        });


        //Todo add photo

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    /**
     * ViewHolder holds data for state of cell-row in recyclerView
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        public LayoutViewholderArtistListBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }
}
