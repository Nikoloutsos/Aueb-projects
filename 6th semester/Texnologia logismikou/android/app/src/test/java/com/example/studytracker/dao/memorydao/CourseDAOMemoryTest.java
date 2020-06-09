package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.domain.Course;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CourseDAOMemoryTest {


    private CourseDAO daoMemory;

    @Before
    public void setUp(){
        daoMemory = new CourseDAOMemory();
    }

    @Test
    public void find() {

    }

    @Test
    public void save() {
        Course course = new Course("TEST", "TEST", 1, 1);
        daoMemory.save(course);
        List<Course> all = daoMemory.findAll();
        assertTrue(all.contains(course));
    }

    @Test
    public void delete() {
        Course course = new Course("TEST", "TEST", 1, 1);
        daoMemory.save(course);
        daoMemory.delete(course.getId());
        assertTrue(daoMemory.findAll().isEmpty());
    }

    @Test
    public void findAll() {

    }

    @Test
    public void nextId() {
    }
}