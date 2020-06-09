package com.example.studytracker.view.homePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityMenuBinding;
import com.example.studytracker.domain.Student;
import com.example.studytracker.view.course.watchCourseList.CoursesActivity;
import com.example.studytracker.view.courseRegistration.watchCourseRegistration.CourseRegistrationActivity;
import com.example.studytracker.view.reminder.watchReminderList.RemindersActivity;
import com.example.studytracker.view.statistic.StatisticsActivity;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Student}
 * to show the menu
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class MenuActivity extends AppCompatActivity implements MenuView {
    ActivityMenuBinding binding;
    MenuPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Menu");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        presenter = new MenuPresenter(this);

        setClickListeners();
    }

    private void setClickListeners() {
        binding.coursesBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickCourses();
            }
        });

        binding.courseRegistrationBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickCourseRegistration();
            }
        });

        binding.statisticsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickStatistics();
            }
        });

        binding.reminderBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickReminders();
            }
        });
    }

    @Override
    public void manageCourses() {
        startActivity(new Intent(MenuActivity.this, CoursesActivity.class));
    }

    @Override
    public void manageCourseRegistration() {
        startActivity(new Intent(MenuActivity.this, CourseRegistrationActivity.class));
    }

    @Override
    public void manageReminders() {
        startActivity(new Intent(MenuActivity.this, RemindersActivity.class));
    }

    @Override
    public void manageStatistics() {
        startActivity(new Intent(MenuActivity.this, StatisticsActivity.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }
}
