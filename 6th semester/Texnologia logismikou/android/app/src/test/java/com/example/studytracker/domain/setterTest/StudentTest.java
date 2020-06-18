package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.Student;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class StudentTest {
    Student student;

    @Before
    public  void init(){
        student = new Student();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setNoSemesters(){
        student.setNoSemesters(-5);
    }

    @Test
    public void check_successful_setNoSemesters(){
        student.setNoSemesters(10);
        Assert.assertTrue(10 == student.getNoSemesters());
    }
}