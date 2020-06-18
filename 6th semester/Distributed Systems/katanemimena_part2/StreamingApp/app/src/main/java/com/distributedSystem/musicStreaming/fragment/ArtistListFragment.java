package com.distributedSystem.musicStreaming.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.adapter.ArtistListAdapter;
import com.distributedSystem.musicStreaming.databinding.FragmentArtistListBinding;
import com.distributedSystem.musicStreaming.model.ArtistConnectionInfo;
import com.distributedSystem.musicStreaming.viewmodel.ArtistListViewModel;

import java.util.List;


public class ArtistListFragment extends Fragment {

    private LiveData<List<ArtistConnectionInfo>> onArtistList;
    private ArtistListViewModel viewModel;
    private FragmentArtistListBinding binding;

    private ArtistListAdapter adapter;

    public ArtistListFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_artist_list, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(ArtistListViewModel.class);
        viewModel.context = getActivity();
        onArtistList = viewModel.getOnArtistList();

        /** Initialize recyclerView*/
        binding.artistListRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        onArtistList.observe(this, new Observer<List<ArtistConnectionInfo>>() {
            @Override
            public void onChanged(List<ArtistConnectionInfo> artistConnectionInfos) {
                if (artistConnectionInfos == null) return;

                binding.loader.setVisibility(View.GONE);
//                Toast.makeText(getActivity(), "Boom data was passed into UI", Toast.LENGTH_SHORT).show();
                adapter = new ArtistListAdapter(artistConnectionInfos, getActivity());
                binding.artistListRv.setAdapter(adapter);

            }
        });

        viewModel.fetchArtists();
    }
}
