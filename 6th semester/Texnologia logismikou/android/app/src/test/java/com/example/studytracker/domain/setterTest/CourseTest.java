package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.Course;

import org.junit.Assert;
import org.junit.Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

public class CourseTest {
    Course course;

    @Before
    public void init(){
        course = new Course("Texnologia logismikou", "Good lesson", 8, 6);
    }

    @Test(expected = IllegalArgumentException.class)
    public void course_constructor() {
        new Course("", "", -2, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void course_set_name() {
        course.setName("Mathimatika");
        course.setName("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void course_set_ects() {
        course.setEcts(7);
        course.setEcts(-1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void course_set_semester() {
        course.setSemester(4);
        course.setSemester(-1);
    }
}
