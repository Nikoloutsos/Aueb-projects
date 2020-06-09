package com.example.studytracker.domain;

import java.util.ArrayList;
import java.util.List;

public class CoursesRegistration {
    private int id;
    private int studentId;
    private String title;
    private AcademicPeriod academicPeriod;
    private List<RegisteredCourse> registeredCourses;


    public CoursesRegistration() {
    }

    public CoursesRegistration(String title, AcademicPeriod academicPeriod) {
        setTitle(title);
        this.academicPeriod = academicPeriod;
        registeredCourses = new ArrayList<>();
    }

    public CoursesRegistration(String title, AcademicPeriod academicPeriod, List<RegisteredCourse> registeredCourses) {
        setTitle(title);
        this.academicPeriod = academicPeriod;
        this.registeredCourses = registeredCourses;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) throw new IllegalArgumentException();
        this.title = title;
    }

    public AcademicPeriod getAcademicPeriod() {
        return academicPeriod;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RegisteredCourse> getRegisteredCourses() {
        return registeredCourses;
    }

    public void setRegisteredCourses(List<RegisteredCourse> registeredCourses) {
        this.registeredCourses = registeredCourses;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }
}
