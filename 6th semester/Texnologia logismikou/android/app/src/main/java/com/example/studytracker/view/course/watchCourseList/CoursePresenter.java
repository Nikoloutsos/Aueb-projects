package com.example.studytracker.view.course.watchCourseList;

import com.example.studytracker.dao.daoStub.CourseDAO;

public class CoursePresenter {
    CourseView view;
    CourseDAO courseDAO;

    public CoursePresenter(CourseView view, CourseDAO courseDAO) {
        this.view = view;
        this.courseDAO = courseDAO;
    }

    public void loadAllCourses() {
        view.onFetchCourses(courseDAO.findAll());
    }

    public void onClickAddCourse() {
        view.onAddCourse();
    }

}
