package com.example.studytracker.view.courseRegistration.watchCourseRegistration;

import com.example.studytracker.dao.daoStub.CourseRegistrationDAO;
import com.example.studytracker.domain.CoursesRegistration;

import java.util.List;

public class CourseRegistrationPresenter {
    CourseRegistrationView view;
    CourseRegistrationDAO courseRegistrationDAO;

    public CourseRegistrationPresenter(CourseRegistrationView view, CourseRegistrationDAO courseRegistrationDAO) {
        this.view = view;
        this.courseRegistrationDAO = courseRegistrationDAO;
    }

    public void loadAllCourseRegistration() {
        List<CoursesRegistration> coursesRegistrationList = courseRegistrationDAO.findAll();
        view.loadAllCourseRegistration(coursesRegistrationList);
    }

    public void onClickAddCourseRegistration() {
        view.onAddCourseRegistration();
    }
}
