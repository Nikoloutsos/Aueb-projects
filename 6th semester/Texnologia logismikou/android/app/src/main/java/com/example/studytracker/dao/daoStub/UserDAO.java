package com.example.studytracker.dao.daoStub;

import com.example.studytracker.domain.User;

/**
 * Data Access Object interface for doing CRUD operations related to {@link User}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public interface UserDAO {
    /**
     * Find a {@link User}
     *
     * @param email             Email used when user registered in app
     * @param encryptedPassword encrypted user's password
     * @return The User or {@code null} if no user found with such credentials.
     */
    User getUserFromCredentials(String email, String encryptedPassword);

    /**
     * Checks if an email is taken by another user
     *
     * @param email email you need to check if already exists
     * @return
     */
    boolean isEmailTaken(String email);

    /**
     * Either saves the {@link User} in storage or updates an existing one if already exists
     *
     * @param user The user you want to either save or update in storage.
     */
    void register(User user);

    /**
     * Removes the {@link User}
     *
     * @param userId The unique id of the course
     */
    void delete(int userId);

    /**
     * @return A unique integer identifier for next saved {@link User}
     */
    int nextId();
}
