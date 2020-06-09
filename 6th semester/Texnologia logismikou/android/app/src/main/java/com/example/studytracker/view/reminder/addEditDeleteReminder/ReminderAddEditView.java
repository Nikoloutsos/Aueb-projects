package com.example.studytracker.view.reminder.addEditDeleteReminder;

import com.example.studytracker.domain.Time;

public interface ReminderAddEditView {

    String getToolbarTitle();

    void setToolbarTitle(String title);

    Integer getMode();

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    String getDate();

    void setDate(String date);

    Time getTime();

    void setTime(String time);

    void setTime(Time time);

    Integer getReminderId();

    void setSaveButtonText(String buttonText);

    void showDatePicker();

    void showTimePicker();

    void showDeleteButton();

    void hideDeleteButton();

    void showEmptyFields();

    void showReminderAdded();

    void showReminderEdited();

    void showReminderDeleted();
}
