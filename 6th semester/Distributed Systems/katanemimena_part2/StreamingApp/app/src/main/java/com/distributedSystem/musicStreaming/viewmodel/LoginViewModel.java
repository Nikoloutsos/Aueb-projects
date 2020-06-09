package com.distributedSystem.musicStreaming.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.distributedSystem.musicStreaming.databinding.ActivityLoginBinding;
import com.distributedSystem.musicStreaming.network.Constants;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<Boolean> onSuccessfulLogin;
    private MutableLiveData<Boolean> onFailedLogin;

    public LoginViewModel(){
        onSuccessfulLogin = new MutableLiveData<>();
        onFailedLogin = new MutableLiveData<>();
    }


    public void onSignInButtonClicked(String username, String password){//TODO SIMULATE A LOGIN WITH A SMALL DELAY
        if(username.equals(Constants.USERNAME) && password.equals(Constants.PASSWORD)){
            onSuccessfulLogin.setValue(true);
        }else{
            onFailedLogin.setValue(true);
        }
    }


    /**
     * Getters for livedata
     */

    public LiveData<Boolean> getOnSuccessfulLogin() {
        return onSuccessfulLogin;
    }

    public LiveData<Boolean> getOnFailedLogin() {
        return onFailedLogin;
    }
}
