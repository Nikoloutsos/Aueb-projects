package com.example.studytracker.dao.daoStub;

import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.Reminder;

import java.util.List;

/**
 * Data Access Object interface for doing CRUD operations related to {@link Course}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public interface ReminderDAO {
    /**
     * Find a {@link Reminder}
     *
     * @param reminderId The unique id of the reminder
     * @return The reminder or {@code null} if not found.
     */
    Reminder find(int reminderId);

    /**
     * Either saves the {@link Reminder} in storage or updates an existing one if already exists
     *
     * @param reminder The reminder you want to either save or update in storage.
     */
    void save(Reminder reminder);

    /**
     * Removes the {@link Reminder}
     *
     * @param reminderId The unique id of the reminder
     */
    void delete(int reminderId);

    /**
     * Get all {@link Reminder}
     *
     * @return All {@link Reminder} created from this management user that are in storage.
     */
    List<Reminder> findAll();

    /**
     * @return A unique integer identifier for next saved {@link Reminder}
     */
    int nextId();
}
