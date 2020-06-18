package com.example.studytracker.domain;


import java.util.List;

public class ManagementUser extends User {
    private Time validUntil;
    private List<Message> messages;

    public ManagementUser(String name, String mail, String bio, String password, Time validUntil) {
        super(name, mail, bio, password);
        this.validUntil = validUntil;
    }

    public Time getValidUntil() {
        return validUntil;
    }

}
