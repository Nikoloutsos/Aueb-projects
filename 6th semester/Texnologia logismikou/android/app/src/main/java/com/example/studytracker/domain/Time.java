package com.example.studytracker.domain;

import java.util.Calendar;

public class Time {
    private int date;
    private int month;
    private int year;
    private int minutes;
    private int hour;
    private int second;


    public Time() {

    }

    private Time(int year, int month, int date, int hour, int minutes, int seconds) {
        this.year = year;
        this.month = month;
        this.date = date;

        this.hour = hour;
        this.minutes = minutes;
        this.second = seconds;
    }

    public static Time createTime(int year, int month, int date, int hour, int minutes, int seconds) {
        if (year < 0 || month > 12 || month < 0 || date < 0
                || date > 31 || hour < 0 || hour > 24 || minutes < 0
                || minutes > 60 || seconds < 0 || seconds > 60) {
            throw new IllegalArgumentException("");
        }

        return new Time(year, month, date, hour, minutes, seconds);
    }

    public static Time getTimeByCalendar(Calendar calendar) {
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        return Time.createTime(year, month, day, hour, minute, second);
    }

    public boolean isBefore(Time when) {
        if (year > when.year) {
            return false;
        } else if (year < when.year) {
            return true;
        }

        if (month > when.month) {
            return false;
        } else if (month < when.month) {
            return true;
        }

        if (date > when.date) {
            return false;
        } else if (date < when.date) {
            return true;
        }

        if (hour > when.hour) {
            return false;
        } else if (hour < when.hour) {
            return true;
        }

        if (minutes > when.minutes) {
            return false;
        } else if (minutes < when.minutes) {
            return true;
        }

        if (second > when.second) {
            return false;
        } else if (second < when.second) {
            return true;
        }
        return true; // Same time
    }

    public String getReadableTime() {
        String monthString = month >= 10 ? "" + month : "0" + month;
        String dateString = date >= 10 ? "" + date : "0" + date;
        String hourString = hour >= 10 ? "" + hour : "0" + hour;
        String minuteString = minutes >= 10 ? "" + minutes : "0" + minutes;

        return year + "-" + monthString + "-" + dateString + " " + hourString + ":" + minuteString;
    }

    public String getDateReadable() {
        String monthString = month >= 10 ? "" + month : "0" + month;
        String dateString = date >= 10 ? "" + date : "0" + date;


        return year + "-" + monthString + "-" + dateString;

    }

    public String getTimeReadable() {
        String hourString = hour >= 10 ? "" + hour : "0" + hour;
        String minuteString = minutes >= 10 ? "" + minutes : "0" + minutes;

        return hourString + ":" + minuteString;
    }

    /**
     * Getters and setters
     */
    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        if (date <= 0 || date > 31) {
            throw new IllegalArgumentException();
        }
        this.date = date;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        if (month < 0 || month > 12) throw new IllegalArgumentException();
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        if (year < 0) throw new IllegalArgumentException();
        this.year = year;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (minutes < 0 || minutes > 60) throw new IllegalArgumentException();
        this.minutes = minutes;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        if (hour < 0 || hour > 24) throw new IllegalArgumentException();
        this.hour = hour;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        if (second < 0 || second > 60) throw new IllegalArgumentException();
        this.second = second;
    }
}
