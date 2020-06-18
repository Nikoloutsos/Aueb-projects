package com.example.studytracker.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;

import static org.junit.Assert.*;

public class TimeTest {
    Time time;

    @Before
    public void init(){
        time = Time.createTime(2020, 11, 22, 12, 33, 11);
    }

    @Test
    public void isBefore1() {
        Time time1 = Time.createTime(2020, 11, 22, 21, 00, 00);
        Time time2 = Time.createTime(2020, 11, 23, 21, 00, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore2() {
        Time time1 = Time.createTime(2019, 11, 21, 21, 00, 00);
        Time time2 = Time.createTime(2020, 11, 22, 21, 00, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore3() {
        Time time1 = Time.createTime(2020, 10, 22, 21, 00, 00);
        Time time2 = Time.createTime(2020, 11, 22, 21, 00, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore4() {
        Time time1 = Time.createTime(2020, 11, 22, 21, 00, 00);
        Time time2 = Time.createTime(2020, 11, 22, 21, 05, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore5() {
        Time time1 = Time.createTime(2019, 12, 22, 21, 00, 00);
        Time time2 = Time.createTime(2020, 11, 22, 22, 00, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore6() {
        Time time1 = Time.createTime(2020, 12, 22, 22, 00, 00);
        Time time2 = Time.createTime(2020, 12, 22, 22, 00, 05);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test
    public void isBefore7() {
        Time time1 = Time.createTime(2020, 12, 22, 22, 00, 00);
        Time time2 = Time.createTime(2020, 12, 22, 22, 00, 00);

        boolean result = time1.isBefore(time2);
        Assert.assertTrue(result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void isBefore_illegal_input() {
        Time time1 = Time.createTime(2020, 11, 44, 21, -1, 00);
    }

    @Test
    public void getReadableTime1() {
        Time time1 = Time.createTime(2020, 11, 22, 12, 33, 11);
        String readableTime = time1.getReadableTime();
        Assert.assertEquals(readableTime, "2020-11-22 12:33:11");
    }

    @Test(expected = IllegalArgumentException.class)
    public void setSecond() {
        time.setSecond(22);
        time.setSecond(65);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setHour() {
        time.setHour(12);
        time.setHour(65);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMinutes() {
        time.setMinutes(22);
        time.setMinutes(65);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setYear() {
        time.setYear(1999);
        time.setSecond(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMonth() {
        time.setMonth(11);
        time.setMonth(33);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDate() {
        time.setSecond(22);
        time.setSecond(65);
    }


    @Test
    public void create_time_from_calendar(){
        Time timeByCalendar = Time.getTimeByCalendar(Calendar.getInstance());
        System.out.println("t");
    }
}