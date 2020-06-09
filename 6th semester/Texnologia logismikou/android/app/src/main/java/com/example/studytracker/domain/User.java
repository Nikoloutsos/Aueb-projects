package com.example.studytracker.domain;

import com.example.studytracker.util.Cryptography;
import com.example.studytracker.util.RegexUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    private int id;
    private String name;
    private String mail;
    private String bio;
    private String encryptedPassword;

    private List<Course> courses = new ArrayList<>();
    private List<CoursesRegistration> coursesRegistrations = new ArrayList<>();

    public User() {
    }

    public User(String name, String mail, String bio, String password) {
        this.bio = bio;
        setMail(mail);
        setName(name);
        this.encryptedPassword = password;
    }

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        if (mail.isEmpty()) throw new IllegalArgumentException();
        if (!RegexUtil.isEmail(mail)) throw new IllegalArgumentException();
        this.mail = mail;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.encryptedPassword = Cryptography.md5(password);
    }

    public List<CoursesRegistration> getCoursesRegistrations() {
        return coursesRegistrations;
    }

    public void setCoursesRegistrations(List<CoursesRegistration> coursesRegistrations) {
        this.coursesRegistrations = coursesRegistrations;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
