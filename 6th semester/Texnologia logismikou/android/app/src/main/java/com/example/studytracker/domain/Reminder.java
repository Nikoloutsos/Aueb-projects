package com.example.studytracker.domain;

public class Reminder {
    private int id;
    private String title;
    private String description;
    private Time timeToRemind;
    private RegisteredCourse registeredCourse;

    public Reminder() {
    }

    public Reminder(String title, String description, Time timeToRemind, RegisteredCourse registeredCourse) {
        this.title = title;
        this.description = description;
        this.timeToRemind = timeToRemind;
        this.registeredCourse = registeredCourse;
    }

    public Reminder(String title, String description, Time timeToRemind) {
        this.title = title;
        this.description = description;
        this.timeToRemind = timeToRemind;
        this.registeredCourse = null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getTimeToRemind() {
        return timeToRemind;
    }

    public void setTimeToRemind(Time timeToRemind) {
        this.timeToRemind = timeToRemind;
    }

    public RegisteredCourse getRegisteredCourse() {
        return registeredCourse;
    }

    public void setRegisteredCourse(RegisteredCourse registeredCourse) {
        this.registeredCourse = registeredCourse;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
