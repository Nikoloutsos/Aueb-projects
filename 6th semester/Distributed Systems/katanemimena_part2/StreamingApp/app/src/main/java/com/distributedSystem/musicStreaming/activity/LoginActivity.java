package com.distributedSystem.musicStreaming.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.distributedSystem.musicStreaming.R;
import com.distributedSystem.musicStreaming.databinding.ActivityLoginBinding;
import com.distributedSystem.musicStreaming.viewmodel.LoginViewModel;
import com.distributedSystem.musicStreaming.viewmodel.SplashScreenViewModel;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    LoginViewModel viewModel;

    LiveData<Boolean> onSuccessfulLogin;
    LiveData<Boolean> onFailedLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        /**
         * Creating viewmodel
         */
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        onSuccessfulLogin = viewModel.getOnSuccessfulLogin();
        onFailedLogin = viewModel.getOnFailedLogin();


        /**
         * Set click listeners for views
         */
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = binding.usernameEdittext.getText().toString();
                String password = binding.passwordEdittext.getText().toString();
                viewModel.onSignInButtonClicked(username, password);
            }
        });


//        startActivity(new Intent(LoginActivity.this, MainActivity.class));
//        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();


        /**
         * Observing livedata for events
         */
        onSuccessfulLogin.observe(this , new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                //TODO
                if(aBoolean)
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();

            }
        });
        onFailedLogin.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean)
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
