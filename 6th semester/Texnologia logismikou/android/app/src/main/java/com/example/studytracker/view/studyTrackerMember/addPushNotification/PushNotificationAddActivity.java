package com.example.studytracker.view.studyTrackerMember.addPushNotification;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityStudyTrackerMemberAddMessageBinding;
import com.example.studytracker.domain.ManagementUser;
import com.example.studytracker.util.AndroidUtil;
import com.example.studytracker.util.pushNotification.FirebaseManager;

/**
 * A subclass of {@link AppCompatActivity} used for {@link ManagementUser}
 * to create push notifications
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class PushNotificationAddActivity extends AppCompatActivity implements PushNotificationAddView {
    ActivityStudyTrackerMemberAddMessageBinding binding;
    PushNotificationAddPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Send a notification");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_tracker_member_add_message);
        presenter = new PushNotificationAddPresenter(this, new FirebaseManager());

        setClickListeners();
    }

    private void setClickListeners() {
        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.sendPushNotification();
            }
        });
    }

    @Override
    public String getName() {
        return binding.titleEt.getText().toString();
    }

    @Override
    public String getDescription() {
        return binding.descriptionEt.getText().toString();
    }

    @Override
    public void showSuccessPushNotificationSend() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Notification send succesfully",
                "Sooner or later all students will be notified",
                "OKAY",
                runnable);
    }

    @Override
    public void showEmptyFields() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {

            }
        };
        AndroidUtil.showDialog(this,
                "Empty fields",
                "Please fill in all fields to send the push notification",
                "OKAY",
                runnable);
    }
}
