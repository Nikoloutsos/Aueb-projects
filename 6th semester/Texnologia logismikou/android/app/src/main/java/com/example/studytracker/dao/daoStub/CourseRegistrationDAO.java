package com.example.studytracker.dao.daoStub;

import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.CoursesRegistration;

import java.util.List;

/**
 * Data Access Object interface for doing CRUD operations related to {@link CoursesRegistration}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public interface CourseRegistrationDAO {
    /**
     * Find a {@link CoursesRegistration}
     *
     * @param courseId The unique id of the course
     * @return The course or {@code null} if not found.
     */
    CoursesRegistration find(int courseId);

    /**
     * Either saves the {@link CoursesRegistration} in storage or updates an existing one if already exists
     *
     * @param coursesRegistration The coursesRegistration you want to either save or update in storage.
     */
    void save(CoursesRegistration coursesRegistration);

    /**
     * Removes the {@link CoursesRegistration}
     *
     * @param CoursesRegistrationId The unique id of the course
     */
    void delete(int CoursesRegistrationId);

    /**
     * Get all {@link Course}
     *
     * @return All {@link Course} that are in storage
     */
    List<CoursesRegistration> findAll();

    /**
     * @return A unique integer identifier for next saved {@link CoursesRegistration}
     */
    int nextId();
}
