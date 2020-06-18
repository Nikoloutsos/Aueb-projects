package com.distributedSystem.musicStreaming.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.adapter.OfflineSongListAdapter;
import com.distributedSystem.musicStreaming.databinding.FragmentOfflineListBinding;
import com.distributedSystem.musicStreaming.network.Constants;
import com.distributedSystem.musicStreaming.util.MusicDownloaderManager;
import com.distributedSystem.musicStreaming.util.OfflineStorage;

import org.json.JSONObject;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineListFragment extends Fragment {
    FragmentOfflineListBinding binding;
    MusicDownloaderManager manager;



    public OfflineListFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline_list, container, false);
        manager = new MusicDownloaderManager(getActivity().getApplicationContext());
        List<JSONObject> listOfSavedMusic = manager.getListOfSavedMusic();

        if (listOfSavedMusic.size() == 0){
            binding.emptyRvPlaceholder.setVisibility(View.VISIBLE);
        }else{
            binding.emptyRvPlaceholder.setVisibility(View.GONE);
        }
        OfflineSongListAdapter adapter = new OfflineSongListAdapter(getActivity(), listOfSavedMusic);
        binding.rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.rv.setAdapter(adapter);

        return binding.getRoot();
    }



}
