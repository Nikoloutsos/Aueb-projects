package com.example.studytracker.domain;

public class Course {

    private int id;
    private String name;
    private String description;
    private int ects;
    private int semester;


    public Course() {
    }

    public Course(String name, String description, int ects, int semester) {
        this.description = description;
        setEcts(ects);
        setName(name);
        setSemester(semester);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        if (ects < 0) {
            throw new IllegalArgumentException();
        }
        this.ects = ects;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        if (semester < 0) {
            throw new IllegalArgumentException();
        }
        this.semester = semester;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
