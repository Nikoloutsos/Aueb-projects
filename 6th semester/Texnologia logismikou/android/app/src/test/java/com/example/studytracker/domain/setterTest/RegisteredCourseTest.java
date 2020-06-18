package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.RegisteredCourse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class RegisteredCourseTest {

    RegisteredCourse registeredCourse;

    @Before
    public  void init(){
        registeredCourse = new RegisteredCourse();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setGrade(){
        registeredCourse.setGrade(-10);
    }

    @Test
    public void check_successful_setGrade(){
        registeredCourse.setGrade(10);
        Assert.assertTrue(10 == registeredCourse.getGrade());
    }

}