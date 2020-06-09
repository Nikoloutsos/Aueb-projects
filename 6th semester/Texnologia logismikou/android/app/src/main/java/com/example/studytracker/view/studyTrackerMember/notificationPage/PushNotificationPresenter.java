package com.example.studytracker.view.studyTrackerMember.notificationPage;

public class PushNotificationPresenter {
    PushNotificationView view;

    public PushNotificationPresenter(PushNotificationView view) {
        this.view = view;
    }

    public void onClickAddPushNotification() {
        view.onAddPushNotification();
    }
}
