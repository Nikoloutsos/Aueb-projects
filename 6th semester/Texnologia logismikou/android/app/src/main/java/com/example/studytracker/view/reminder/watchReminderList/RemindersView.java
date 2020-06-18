package com.example.studytracker.view.reminder.watchReminderList;

import com.example.studytracker.domain.Reminder;

import java.util.List;

public interface RemindersView {

    void loadAllReminders(List<Reminder> reminders);

    void onClickAddReminder();
}
