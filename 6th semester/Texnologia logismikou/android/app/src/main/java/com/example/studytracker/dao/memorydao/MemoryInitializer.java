package com.example.studytracker.dao.memorydao;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.dao.daoStub.CourseRegistrationDAO;
import com.example.studytracker.dao.daoStub.Initializer;
import com.example.studytracker.dao.daoStub.ReminderDAO;
import com.example.studytracker.dao.daoStub.UserDAO;

/**
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 * @see Initializer
 */
public class MemoryInitializer extends Initializer {
    @Override
    public void eraseAllData() {
        CourseDAOMemory.courseList.clear();
        CoursesRegistrationDAOMemory.coursesRegistrationsList.clear();
        ReminderDAOMemory.reminderList.clear();
        UserDAOMemory.usersList.clear();
    }

    @Override
    public CourseDAO getCourseDAO() {
        return new CourseDAOMemory();
    }

    @Override
    public CourseRegistrationDAO getCourseRegistrationDAO() {
        return new CoursesRegistrationDAOMemory();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOMemory();
    }

    @Override
    public ReminderDAO getReminderDAO() {
        return new ReminderDAOMemory();
    }
}
