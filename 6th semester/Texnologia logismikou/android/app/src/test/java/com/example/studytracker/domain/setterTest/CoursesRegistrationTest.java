package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.AcademicPeriod;
import com.example.studytracker.domain.CoursesRegistration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoursesRegistrationTest {

    CoursesRegistration coursesRegistration;

    @Before
    public  void init(){
        coursesRegistration = new CoursesRegistration();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setTitle(){
        coursesRegistration.setTitle("");
    }

    @Test
    public void check_successful_setTitle(){
        coursesRegistration.setTitle("1stRegistration");
        Assert.assertEquals("1stRegistration", coursesRegistration.getTitle());
    }

}