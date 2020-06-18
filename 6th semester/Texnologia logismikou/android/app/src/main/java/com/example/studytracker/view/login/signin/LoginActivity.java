package com.example.studytracker.view.login.signin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityLoginBinding;
import com.example.studytracker.domain.ManagementUser;
import com.example.studytracker.domain.Student;
import com.example.studytracker.util.AndroidUtil;
import com.example.studytracker.util.SharedPrefUtil;
import com.example.studytracker.view.homePage.MenuActivity;
import com.example.studytracker.view.login.signup.RegisterActivity;
import com.example.studytracker.view.studyTrackerMember.notificationPage.PushNotificationActivity;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student} and {@link ManagementUser}
 * to login
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class LoginActivity extends AppCompatActivity implements LoginView {
    ActivityLoginBinding binding;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToolbarTitle("Log in");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

        presenter = new LoginPresenter(this,
                App.memoryInitializer.getUserDAO(),
                new SharedPrefUtil());

        setUpClickListeners();
    }

    private void setUpClickListeners() {
        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onLogin();
            }
        });

        binding.createAccountTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onCreateAnAccount();
            }
        });
    }

    @Override
    public void onSuccessfulLogin() {
        startActivity(new Intent(LoginActivity.this, MenuActivity.class));
        finish();
    }

    @Override
    public void onCreateAnAccount() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
    public String getPassword() {
        return binding.passwordEt.getText().toString();
    }

    @Override
    public void setPassword(String password) {
        binding.passwordEt.setText(password);
    }

    @Override
    public void setToolbarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void showEmptyFieldsDetected() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        AndroidUtil.showDialog(this,
                "Empty fields",
                "Please insert email and password to login",
                "OKAY",
                runnable);
    }

    @Override
    public void showFailedLogin() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
            }
        };
        AndroidUtil.showDialog(this,
                "Couldn't login",
                "Wrong username or password. \nPlease try again",
                "OKAY",
                runnable);
    }

    @Override
    public void showSuccessStudentLogin() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Successful login",
                "Welcome back student",
                "Enter the app",
                runnable);
    }

    @Override
    public void showSuccessAdminLogin() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(LoginActivity.this, PushNotificationActivity.class));
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Successful login (ADMIN)",
                "Welcome back ",
                "Enter the app",
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
                "Invalid email",
                "Email is not valid",
                "OKAY",
                runnable);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
