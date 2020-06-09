package com.example.studytracker.view.login.signup;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityRegisterBinding;
import com.example.studytracker.domain.Student;
import com.example.studytracker.util.AndroidUtil;
import com.example.studytracker.util.SharedPrefUtil;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to register
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class RegisterActivity extends AppCompatActivity implements RegisterView {
    ActivityRegisterBinding binding;
    RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Register");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        presenter = new RegisterPresenter(this,
                App.memoryInitializer.getUserDAO(),
                new SharedPrefUtil());


        setClickListeners();
    }

    private void setClickListeners() {
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onRegister();
            }
        });
    }

    @Override
    public void onSuccessfulRegister() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Register successfully",
                "Congratulations " + getUsername() + ",\nYou can now use the app",
                "OKAY",
                runnable);
    }

    @Override
    public String getEmail() {
        return binding.emailEt.getText().toString();
    }

    @Override
    public void setEmail(String email) {
        binding.emailEt.setText(email);
    }

    @Override
    public String getUsername() {
        return binding.usernameEt.getText().toString();
    }

    @Override
    public void setUsername(String username) {
        binding.usernameEt.setText(username);
    }

    @Override
    public String getPassword() {
        return binding.passwordEt.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        binding.passwordEt.setText(password);
    }


    @Override
    public void showEmptyFieldsDetected() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Register failed",
                "Empty fields detected. \nFill them to register..",
                "OKAY",
                runnable);
    }

    @Override
    public void showEmailAlreadyExists() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Register failed",
                "Email is taken. In case of forgetting your password contact with our team",
                "OKAY",
                runnable);
    }

    @Override
    public void showInvalidEmail() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Invalid Email",
                "Invalid email detected. Please use your email",
                "OKAY",
                runnable);
    }
}
