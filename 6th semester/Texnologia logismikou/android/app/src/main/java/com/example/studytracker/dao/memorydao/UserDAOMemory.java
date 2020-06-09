package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.UserDAO;
import com.example.studytracker.domain.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A memory implementation of {@link UserDAO}
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class UserDAOMemory implements UserDAO {
    protected static List<User> usersList = new ArrayList<>();
    private static int autoIncrement = 0;

    @Override
    public User getUserFromCredentials(String email, String encryptedPassword) {
        for (User user : usersList) {
            if (user.getMail().equalsIgnoreCase(email) && user.getEncryptedPassword().equalsIgnoreCase(encryptedPassword)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean isEmailTaken(String email) {
        for (User user : usersList) {
            if (user.getMail().equalsIgnoreCase(email)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void register(User user) {
        delete(user.getId());
        user.setId(nextId());
        usersList.add(user);
    }

    @Override
    public void delete(int userId) {
        for (User c : usersList) {
            if (c.getId() == userId) {
                usersList.remove(c);
                break;
            }
        }
    }

    @Override
    public int nextId() {
        autoIncrement++;
        return autoIncrement;
    }
}
