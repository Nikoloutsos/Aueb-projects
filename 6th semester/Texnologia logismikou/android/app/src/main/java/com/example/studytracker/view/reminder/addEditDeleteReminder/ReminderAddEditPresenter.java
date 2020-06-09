package com.example.studytracker.view.reminder.addEditDeleteReminder;

import com.example.studytracker.dao.daoStub.ReminderDAO;
import com.example.studytracker.domain.Reminder;
import com.example.studytracker.domain.Time;

public class ReminderAddEditPresenter {
    ReminderAddEditView view;
    ReminderDAO reminderDAO;


    public ReminderAddEditPresenter(ReminderAddEditView view, ReminderDAO reminderDAO) {
        this.view = view;
        this.reminderDAO = reminderDAO;
    }

    public void setUp() {
        if (isAddMode()) {
            setUpAddMode();
        } else if (isEditMode()) {
            setUpEditMode();
        }
    }


    public void onClickSaveButton() {
        String reminderTitle = view.getName();
        String reminderDescription = view.getDescription();
        Time reminderTime = view.getTime();

        if (hasEmptyFields(reminderTitle, reminderDescription)) return;

        createAndSaveReminder(reminderTitle, reminderDescription, reminderTime);

        if (isEditMode()) {
            view.showReminderEdited();
        } else {
            view.showReminderAdded();
        }
    }


    public void onClickDeleteButton() {
        Integer reminderId = view.getReminderId();
        reminderDAO.delete(reminderId);
        view.showReminderDeleted();
    }

    public void onClickDateChange() {
        view.showDatePicker();
    }

    public void onClickTimeChange() {
        view.showTimePicker();
    }

    private boolean isEditMode() {
        return view.getMode() == 2;
    }

    private boolean isAddMode() {
        return view.getMode() == 1;
    }

    private void setUpAddMode() {
        view.setToolbarTitle("Add Reminder");
        view.hideDeleteButton();
    }

    private void setUpEditMode() {
        view.setToolbarTitle("Edit Reminder");
        view.setSaveButtonText("EDIT REMINDER");

        Integer reminderId = view.getReminderId();
        Reminder reminder = reminderDAO.find(reminderId);

        view.setName(reminder.getTitle());
        view.setDescription(reminder.getDescription());
        view.setDate(reminder.getTimeToRemind().getDateReadable());
        view.setTime(reminder.getTimeToRemind().getTimeReadable());
        view.setTime(reminder.getTimeToRemind());
    }

    private void createAndSaveReminder(String reminderTitle, String reminderDescription, Time reminderTime) {
        Reminder reminder = new Reminder(reminderTitle, reminderDescription, reminderTime);
        if (isEditMode()) reminder.setId(view.getReminderId());
        reminderDAO.save(reminder);
    }

    private boolean hasEmptyFields(String reminderTitle, String reminderDescription) {
        if (reminderTitle.isEmpty() || reminderDescription.isEmpty()) {
            view.showEmptyFields();
            return true;
        }
        return false;
    }
}
