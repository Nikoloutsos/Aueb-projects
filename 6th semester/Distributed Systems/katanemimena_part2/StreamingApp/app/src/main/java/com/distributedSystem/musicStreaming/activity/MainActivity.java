package com.distributedSystem.musicStreaming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.databinding.ActivityMainBinding;
import com.distributedSystem.musicStreaming.fragment.ArtistListFragment;
import com.distributedSystem.musicStreaming.fragment.OfflineListFragment;
import com.distributedSystem.musicStreaming.fragment.ProfileFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import nl.joery.animatedbottombar.AnimatedBottomBar;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        binding.animatedBar.selectTab(binding.animatedBar.getTabs().get(0), false);

        ArtistListFragment fragment = new ArtistListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, fragment, "artist").commit();


        binding.animatedBar.setOnTabSelectListener(new AnimatedBottomBar.OnTabSelectListener() {
            @Override
            public void onTabSelected(int i, @Nullable AnimatedBottomBar.Tab tab, int i1, @NotNull AnimatedBottomBar.Tab tab1) {
                switch (i1){
                    case 0:
                        ArtistListFragment fragment = new ArtistListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment, "artist").commit();
                        break;
                    case 1:
                        OfflineListFragment fragment1 = new OfflineListFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment1, "artist").commit();
                        break;
                    case 2:
                        ProfileFragment fragment2 = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment2, "artist").commit();
                        break;

                }
            }

            @Override
            public void onTabReselected(int i, @NotNull AnimatedBottomBar.Tab tab) {

            }
        });

    }
}
