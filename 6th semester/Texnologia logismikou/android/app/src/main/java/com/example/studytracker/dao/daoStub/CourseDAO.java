package com.example.studytracker.dao.daoStub;

import com.example.studytracker.domain.Course;

import java.util.List;

/**
 * Data Access Object interface for doing CRUD operations related to {@link Course}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public interface CourseDAO {

    /**
     * Find a {@link Course}
     *
     * @param courseId The unique id of the course
     * @return The course or {@code null} if not found.
     */
    Course find(int courseId);

    /**
     * Either saves the {@link Course} in storage or updates an existing one if already exists
     *
     * @param course The course you want to either save or update in storage.
     */
    void save(Course course);

    /**
     * Removes the {@link Course}
     *
     * @param courseId The unique id of the course
     */
    void delete(int courseId);

    /**
     * Get all {@link Course}
     *
     * @return All {@link Course} that are in storage
     */
    List<Course> findAll();

    /**
     * @return A unique integer identifier for next saved {@link Course}
     */
    int nextId();
}
