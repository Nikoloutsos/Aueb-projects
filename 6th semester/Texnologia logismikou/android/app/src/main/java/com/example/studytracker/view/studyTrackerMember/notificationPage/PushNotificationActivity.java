package com.example.studytracker.view.studyTrackerMember.notificationPage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityStudyTrackerMemberBinding;
import com.example.studytracker.domain.ManagementUser;
import com.example.studytracker.view.studyTrackerMember.addPushNotification.PushNotificationAddActivity;

/**
 * A subclass of {@link AppCompatActivity} used for {@link ManagementUser}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class PushNotificationActivity extends AppCompatActivity implements PushNotificationView {
    ActivityStudyTrackerMemberBinding binding;
    PushNotificationPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Admin page");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_study_tracker_member);
        presenter = new PushNotificationPresenter(this);

        setClickListeners();
    }

    private void setClickListeners() {
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddPushNotification();
            }
        });
    }

    @Override
    public void onAddPushNotification() {
        startActivity(new Intent(PushNotificationActivity.this, PushNotificationAddActivity.class));
    }
}
