package com.example.studytracker.domain;

public class Message {

    private int id;
    private String title;
    private String description;
    private Time timePosted;

    public Message() {
    }

    public Message(String title, String description, Time time) {
        setTitle(title);
        setDescription(description);
        this.timePosted = time;
    }


    /**
     * Getters and setters
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title.isEmpty()) throw new IllegalArgumentException();
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (description.isEmpty()) throw new IllegalArgumentException();
        this.description = description;
    }

    public Time getTimePosted() {
        return timePosted;
    }

    public void setTimePosted(Time timePosted) {
        this.timePosted = timePosted;
    }

    public int getId() {
        return id;
    }
}
