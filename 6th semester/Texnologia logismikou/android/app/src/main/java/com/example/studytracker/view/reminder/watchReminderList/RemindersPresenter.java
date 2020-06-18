package com.example.studytracker.view.reminder.watchReminderList;

import com.example.studytracker.dao.daoStub.ReminderDAO;
import com.example.studytracker.domain.Reminder;

import java.util.List;

public class RemindersPresenter {
    RemindersView view;
    ReminderDAO reminderDAO;

    public RemindersPresenter(RemindersView view, ReminderDAO reminderDAO) {
        this.view = view;
        this.reminderDAO = reminderDAO;
    }

    public void loadAllReminders() {
        List<Reminder> reminders = reminderDAO.findAll();
        view.loadAllReminders(reminders);
    }

    public void onClickAddReminder() {
        view.onClickAddReminder();
    }
}
