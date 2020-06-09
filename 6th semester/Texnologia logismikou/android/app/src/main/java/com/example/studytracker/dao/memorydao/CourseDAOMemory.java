package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.domain.Course;

import java.util.ArrayList;
import java.util.List;

/**
 * A memory implementation of {@link CourseDAO}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CourseDAOMemory implements CourseDAO {
    protected static List<Course> courseList = new ArrayList<>();
    private static int autoIncrementId = 0;

    public CourseDAOMemory() {
    }

    @Override
    public Course find(int courseId) {
        for (Course c : courseList) {
            if (c.getId() == courseId) return c;
        }
        return null;
    }

    @Override
    public void save(Course course) {
        delete(course.getId());
        course.setId(nextId());
        courseList.add(course);
    }

    @Override
    public void delete(int courseId) {
        for (Course c : courseList) {
            if (c.getId() == courseId) {
                courseList.remove(c);
                break;
            }
        }
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(courseList);
    }

    @Override
    public int nextId() {
        autoIncrementId++;
        return autoIncrementId;
    }
}
