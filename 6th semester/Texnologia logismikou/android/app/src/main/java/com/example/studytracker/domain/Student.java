package com.example.studytracker.domain;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {

    private int noSemesters;
    private List<CoursesRegistration> coursesRegistrations = new ArrayList<>();
    private List<Course> courses = new ArrayList<>();

    public Student() {
    }

    public Student(String name, String mail, String bio, int noSemesters, String password) {
        super(name, mail, bio, password);
        setNoSemesters(noSemesters);
    }

    public Student(String name, String mail, String password) {
        super(name, mail, "", password);
        setNoSemesters(8);
    }

    public int getNoSemesters() {
        return noSemesters;
    }

    public void setNoSemesters(int noSemesters) {
        if (noSemesters <= 0) {
            throw new IllegalArgumentException();
        }
        this.noSemesters = noSemesters;
    }

    @Override
    public List<CoursesRegistration> getCoursesRegistrations() {
        return coursesRegistrations;
    }

    @Override
    public List<Course> getCourses() {
        return courses;
    }
}
