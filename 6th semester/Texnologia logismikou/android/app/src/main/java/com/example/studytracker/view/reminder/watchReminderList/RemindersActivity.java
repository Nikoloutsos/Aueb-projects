package com.example.studytracker.view.reminder.watchReminderList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityRemindersBinding;
import com.example.studytracker.domain.Reminder;
import com.example.studytracker.view.adapter.ReminderListAdapter;
import com.example.studytracker.view.course.addEditDeleteCourse.CourseAddEditActivity;
import com.example.studytracker.view.reminder.addEditDeleteReminder.ReminderAddEditActivity;

import java.util.List;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Reminder}
 * to see all {@link Reminder}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class RemindersActivity extends AppCompatActivity implements RemindersView {
    ActivityRemindersBinding binding;
    RemindersPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("My reminders");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminders);
        presenter = new RemindersPresenter(this, App.memoryInitializer.getReminderDAO());

        setClickListeners();
    }

    private void setClickListeners() {
        binding.fabAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickAddReminder();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.loadAllReminders();
    }

    @Override
    public void loadAllReminders(List<Reminder> reminders) {
        binding.remindersRv.setLayoutManager(new LinearLayoutManager(this));
        binding.remindersRv.setAdapter(new ReminderListAdapter(reminders, this));
    }

    @Override
    public void onClickAddReminder() {
        Intent intent = new Intent(RemindersActivity.this, ReminderAddEditActivity.class);
        intent.putExtra(CourseAddEditActivity.MODE_KEY, CourseAddEditActivity.ADD_MODE);
        startActivity(intent);
    }
}
