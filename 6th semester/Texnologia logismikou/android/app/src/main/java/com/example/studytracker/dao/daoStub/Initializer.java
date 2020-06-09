package com.example.studytracker.dao.daoStub;

import com.example.studytracker.domain.AcademicPeriod;
import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.ManagementUser;
import com.example.studytracker.domain.RegisteredCourse;
import com.example.studytracker.domain.Reminder;
import com.example.studytracker.domain.Student;
import com.example.studytracker.domain.Time;
import com.example.studytracker.domain.User;
import com.example.studytracker.util.Cryptography;

/**
 * {@link Initializer} class is responsible for providing/initializing static data for testing and example purposes.
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public abstract class Initializer {

    /**
     * Removes all data
     */
    public abstract void eraseAllData();

    /**
     * Prepare some fake testing objects and add them in DAOs
     */
    public void prepareData() {
        eraseAllData();
        addUsers();
        AddCourseAndCourseRegistration();
        addReminders();
    }

    private void AddCourseAndCourseRegistration() {
        Course course1 = new Course("Distributed systems", "In this lesson you will learn...", 8, 1);
        Course course2 = new Course("Distributed systems2", "In this lesson you will learn...", 7, 1);
        Course course3 = new Course("Distributed systems3", "In this lesson you will learn...", 6, 2);
        Course course4 = new Course("Distributed systems4", "In this lesson you will learn...", 5, 2);
        Course course5 = new Course("Distributed systems5", "In this lesson you will learn...", 4, 3);
        Course course6 = new Course("Distributed systems6", "In this lesson you will learn...", 3, 3);
        Course course7 = new Course("Distributed systems7", "In this lesson you will learn...", 2, 4);
        Course course8 = new Course("Distributed systems8", "In this lesson you will learn...", 1, 5);

        getCourseDAO().save(course1);
        getCourseDAO().save(course2);
        getCourseDAO().save(course3);
        getCourseDAO().save(course4);
        getCourseDAO().save(course5);
        getCourseDAO().save(course6);
        getCourseDAO().save(course7);
        getCourseDAO().save(course8);

        AcademicPeriod academicPeriod1 = new AcademicPeriod(2017, AcademicPeriod.SPRING);
        AcademicPeriod academicPeriod2 = new AcademicPeriod(2018, AcademicPeriod.AUTUMN);
        AcademicPeriod academicPeriod3 = new AcademicPeriod(2018, AcademicPeriod.SPRING);
        CoursesRegistration coursesRegistration1 = new CoursesRegistration("Egramateia Course registration 2017 SPR", academicPeriod1);
        CoursesRegistration coursesRegistration2 = new CoursesRegistration("Egramateia Course registration 2018 AUT", academicPeriod2);
        CoursesRegistration coursesRegistration3 = new CoursesRegistration("Egramateia Course registration 2018 SPR", academicPeriod3);

        coursesRegistration1.getRegisteredCourses().add(new RegisteredCourse(7, course1));
        coursesRegistration1.getRegisteredCourses().add(new RegisteredCourse(8, course2));
        coursesRegistration2.getRegisteredCourses().add(new RegisteredCourse(6.5, course3));
        coursesRegistration2.getRegisteredCourses().add(new RegisteredCourse(9, course4));
        coursesRegistration3.getRegisteredCourses().add(new RegisteredCourse(10, course5));
        coursesRegistration3.getRegisteredCourses().add(new RegisteredCourse(9, course6));

        getCourseRegistrationDAO().save(coursesRegistration1);
        getCourseRegistrationDAO().save(coursesRegistration2);
        getCourseRegistrationDAO().save(coursesRegistration3);
    }

    private void addReminders() {
        Time time = Time.createTime(2020, 10, 10, 1, 1, 1);
        Time time2 = Time.createTime(2020, 10, 11, 1, 44, 14);
        Time time3 = Time.createTime(2020, 10, 12, 1, 32, 1);


        Reminder reminder1 = new Reminder("Remind me to meet with Jon for homework", "Description", time);
        Reminder reminder2 = new Reminder("Remind me to return a book in the library", "Description", time2);
        Reminder reminder3 = new Reminder("Remind me study for the exams", "Description", time3);

        getReminderDAO().save(reminder1);
        getReminderDAO().save(reminder2);
        getReminderDAO().save(reminder3);
    }

    private void addUsers() {
        ManagementUser managementUser = new ManagementUser("Basilis Zafeiris",
                "admin@admin.gr",
                "I'm a teacher in AUEB",
                Cryptography.md5("123"),
                Time.createTime(2021, 01, 01, 11, 12, 12));

        Student student = new Student("Jim joe",
                "test@test.gr",
                "I'm a teacher in AUEB",
                8,
                Cryptography.md5("123"));

        getUserDAO().register(student);
        getUserDAO().register(managementUser);
    }

    /**
     * @return {@link Course} DAO
     */
    public abstract CourseDAO getCourseDAO();

    /**
     * @return {@link CoursesRegistration} DAO
     */
    public abstract CourseRegistrationDAO getCourseRegistrationDAO();

    /**
     * @return {@link User} DAO
     */
    public abstract UserDAO getUserDAO();

    /**
     * @return {@link Reminder} DAO
     */
    public abstract ReminderDAO getReminderDAO();
}
