package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.CourseRegistrationDAO;
import com.example.studytracker.domain.CoursesRegistration;

import java.util.ArrayList;
import java.util.List;

/**
 * A memory implementation of {@link CourseRegistrationDAO}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class CoursesRegistrationDAOMemory implements CourseRegistrationDAO {
    protected static List<CoursesRegistration> coursesRegistrationsList = new ArrayList<>();
    private static int autoIncrement = 0;

    public CoursesRegistrationDAOMemory() {
    }

    @Override
    public CoursesRegistration find(int courseId) {
        for (CoursesRegistration c : coursesRegistrationsList) {
            if (c.getId() == courseId) return c;
        }
        return null;
    }

    @Override
    public void save(CoursesRegistration coursesRegistration) {
        delete(coursesRegistration.getId());
        coursesRegistrationsList.add(coursesRegistration);
        coursesRegistration.setId(nextId());
    }

    @Override
    public void delete(int CoursesRegistrationId) {
        for (CoursesRegistration c : coursesRegistrationsList) {
            if (c.getId() == CoursesRegistrationId) {
                coursesRegistrationsList.remove(c);
                break;
            }
        }
    }

    @Override
    public List<CoursesRegistration> findAll() {
        return new ArrayList<>(coursesRegistrationsList);
    }

    @Override
    public int nextId() {
        autoIncrement++;
        return autoIncrement;
    }
}
