package com.example.studytracker.view.reminder.addEditDeleteReminder;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.studytracker.App;
import com.example.studytracker.R;
import com.example.studytracker.databinding.ActivityReminderAddEditBinding;
import com.example.studytracker.domain.Reminder;
import com.example.studytracker.domain.Time;
import com.example.studytracker.util.AndroidUtil;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

/**
 * A subclass of {@link AppCompatActivity} used for {@link Reminder}
 * to add/edit/delete {@link Reminder}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class ReminderAddEditActivity extends AppCompatActivity implements ReminderAddEditView {
    public static final String MODE_KEY = "MODE_KEY";
    public static final int ADD_MODE = 1;
    public static final int EDIT_MODE = 2;
    ActivityReminderAddEditBinding binding;
    ReminderAddEditPresenter presenter;
    private Time timeSelected = new Time();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reminder_add_edit);
        presenter = new ReminderAddEditPresenter(this, App.memoryInitializer.getReminderDAO());
        presenter.setUp();

        setUpClickListeners();
    }

    private void setUpClickListeners() {
        binding.saveReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickSaveButton();
            }
        });

        binding.timeDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickDateChange();
            }
        });

        binding.timeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickTimeChange();
            }
        });


        binding.deleteReminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.onClickDeleteButton();
            }
        });
    }


    @Override
    public String getToolbarTitle() {
        String toolbarTitle = super.getTitle().toString();
        return toolbarTitle;
    }

    @Override
    public void setToolbarTitle(String title) {
        super.setTitle(title);
    }

    @Override
    public Integer getMode() {
        int mode = getIntent().getExtras().getInt(MODE_KEY);
        return mode;
    }

    @Override
    public String getName() {
        return binding.titleReminderEt.getText().toString();
    }

    @Override
    public void setName(String name) {
        binding.titleReminderEt.setText(name);
    }

    @Override
    public String getDescription() {
        return binding.descriptionReminderEt.getText().toString();
    }

    @Override
    public void setDescription(String description) {
        binding.descriptionReminderEt.setText(description);
    }

    @Override
    public String getDate() {
        return binding.timeDateEt.getText().toString();
    }

    @Override
    public void setDate(String date) {
        binding.timeDateEt.setText(date);
    }

    @Override
    public Time getTime() {
        return timeSelected;
    }

    @Override
    public void setTime(String time) {
        binding.timeEt.setText(time);
    }

    @Override
    public void setTime(Time time) {
        timeSelected = time;
    }

    @Override
    public Integer getReminderId() {
        int reminderId = getIntent().getExtras().getInt("id");
        return reminderId;
    }

    @Override
    public void setSaveButtonText(String buttonText) {
        binding.saveReminderBtn.setText(buttonText);
    }

    @Override
    public void showDatePicker() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        timeSelected.setYear(year);
                        timeSelected.setMonth(monthOfYear);
                        timeSelected.setDate(dayOfMonth);
                        setDate(timeSelected.getDateReadable());
                    }
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void showTimePicker() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        timeSelected.setHour(hourOfDay);
                        timeSelected.setMinutes(minute);
                        timeSelected.setSecond(second);
                        setTime(timeSelected.getTimeReadable());

                    }
                }, true);
        dpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void showDeleteButton() {
        binding.deleteReminderBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideDeleteButton() {
        binding.deleteReminderBtn.setVisibility(View.GONE);
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
                "Please fill in all fields to add a reminder",
                "Okay",
                runnable);
    }

    @Override
    public void showReminderAdded() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Reminder added",
                getName() + " reminder added!",
                "Okay",
                runnable);
    }

    @Override
    public void showReminderEdited() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Reminder edited",
                getName() + " reminder edited!",
                "Okay",
                runnable);
    }

    @Override
    public void showReminderDeleted() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };
        AndroidUtil.showDialog(this,
                "Reminder deleted",
                getName() + " reminder deleted!",
                "Okay",
                runnable);
    }
}
