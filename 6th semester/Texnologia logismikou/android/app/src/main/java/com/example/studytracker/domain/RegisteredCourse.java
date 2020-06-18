package com.example.studytracker.domain;

import java.util.ArrayList;
import java.util.List;

public class RegisteredCourse {
    private static final int UNDEFINED = -1;

    private int id;
    private double grade = UNDEFINED;
    private Course course;
    private List<Reminder> reminders = new ArrayList<>();

    public RegisteredCourse() {
    }

    public RegisteredCourse(Course course) {
        this.course = course;
    }

    public RegisteredCourse(double grade, Course course) {
        setGrade(grade);
        this.course = course;
    }

    public RegisteredCourse(double grade, Course course, int courseRegistrationID, List<Reminder> reminders) {
        setGrade(grade);
        this.course = course;
        this.reminders = reminders;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade >= 0 && grade <= 10) {
            this.grade = grade;
        } else {
            throw new IllegalArgumentException();
        }
    }

    public Course getCourse() {
        return course;
    }

    public List<Reminder> getReminders() {
        return reminders;
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public int getId() {
        return id;
    }
}
