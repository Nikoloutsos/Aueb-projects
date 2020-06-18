package com.example.studytracker.view.homePage;

public class MenuPresenter {
    MenuView view;

    public MenuPresenter(MenuView view) {
        this.view = view;
    }

    public void onClickCourses() {
        view.manageCourses();
    }

    public void onClickCourseRegistration() {
        view.manageCourseRegistration();
    }

    public void onClickReminders() {
        view.manageReminders();
    }

    public void onClickStatistics() {
        view.manageStatistics();
    }

    void detach() {
        view = null;
    }
}
