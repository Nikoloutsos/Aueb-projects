package com.example.studytracker.view.studyTrackerMember.addPushNotification;

import com.example.studytracker.util.pushNotification.PushNotificationManager;

public class PushNotificationAddPresenter {
    PushNotificationAddView view;
    PushNotificationManager pushNotificationManager;

    public PushNotificationAddPresenter(PushNotificationAddView view, PushNotificationManager pushNotificationManager) {
        this.view = view;
        this.pushNotificationManager = pushNotificationManager;
    }

    public void sendPushNotification() {
        String title = view.getName();
        String description = view.getDescription();

        if (title.isEmpty() || description.isEmpty()) {
            view.showEmptyFields();
            return;
        }

        pushNotificationManager.sendPushNotificationToAllStudents(title, description);
        view.showSuccessPushNotificationSend();
    }
}
