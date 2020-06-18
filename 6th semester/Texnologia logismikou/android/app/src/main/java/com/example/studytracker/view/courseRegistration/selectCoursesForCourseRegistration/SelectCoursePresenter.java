package com.example.studytracker.view.courseRegistration.selectCoursesForCourseRegistration;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.domain.Course;

import java.util.List;

public class SelectCoursePresenter {
    SelectCourseView view;
    CourseDAO courseDAO;

    public SelectCoursePresenter(SelectCourseView view, CourseDAO courseDAO) {
        this.view = view;
        this.courseDAO = courseDAO;
    }

    public void onClickAddCoursesToCourseRegistration() {
        if (view.getSelectedCourses().isEmpty()) {
            view.showNoCourseSelected();
            return;
        }
        view.onClickAddCoursesInCourseRegistration(view.getSelectedCourses());
    }

    public void loadAllCourses() {
        List<Course> allCourses = courseDAO.findAll();
        view.loadAllCourses(allCourses);
    }

}
