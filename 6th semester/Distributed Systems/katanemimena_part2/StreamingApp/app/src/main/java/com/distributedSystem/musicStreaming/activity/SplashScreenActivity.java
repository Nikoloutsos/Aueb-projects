package com.distributedSystem.musicStreaming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.databinding.ActivitySplashScreenBinding;
import com.distributedSystem.musicStreaming.viewmodel.SplashScreenViewModel;

public class SplashScreenActivity extends AppCompatActivity {

    ActivitySplashScreenBinding binding;
    SplashScreenViewModel viewModel;

    LiveData<Boolean> onErrorCommunicatingWithSever;
    LiveData<Boolean> onBadSyncInfoReceived;
    LiveData<Boolean> onSuccesfulZookeeperResponse;

    /**
     * Used for waiting a little bit in splash screen if zookeeper response is super fast
     */
    long timeUserOpenedActivity;
    public static final long TIME_TO_WAIT = 2300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash_screen);

        /**
         * Creating viewmodel
         */
        viewModel = ViewModelProviders.of(this).get(SplashScreenViewModel.class);

        /**
         * activity
         */
        onErrorCommunicatingWithSever = viewModel.getOnErrorCommunicatingWithSever();
        onBadSyncInfoReceived = viewModel.getOnBadSyncInfoReceived();
        onSuccesfulZookeeperResponse = viewModel.getOnSuccesfulZookeeperResponse();
        viewModel.context = getApplicationContext();

        /**
         * Observing livedata for events
         */
        onErrorCommunicatingWithSever.observe(this , new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //TODO
                if(aBoolean)
                    Toast.makeText(SplashScreenActivity.this, "ERROR Communicating with server", Toast.LENGTH_SHORT).show();
            }
        });
        onBadSyncInfoReceived.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //TODO
                if(aBoolean)
                    Toast.makeText(SplashScreenActivity.this, "ERROR Unknown response from server", Toast.LENGTH_SHORT).show();
            }
        });
        onSuccesfulZookeeperResponse.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    moveToLogin();
                }
            }
        });
    }


    public void moveToLogin(){
        long now = System.currentTimeMillis();
        long dif = now - timeUserOpenedActivity;

        if(dif >= TIME_TO_WAIT ){
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        }else{
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
                    finish();
                }
            }, TIME_TO_WAIT - dif);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        /**
         * Start connection with zookeeper
         */
        timeUserOpenedActivity = System.currentTimeMillis();
        viewModel.connectZookeeper();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onBadSyncInfoReceived.removeObservers(this);
        onErrorCommunicatingWithSever.removeObservers(this);
        onSuccesfulZookeeperResponse.removeObservers(this);
    }
}
