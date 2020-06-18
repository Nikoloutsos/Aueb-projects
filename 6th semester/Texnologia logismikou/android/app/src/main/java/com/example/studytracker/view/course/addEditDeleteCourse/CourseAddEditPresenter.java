package com.example.studytracker.view.course.addEditDeleteCourse;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.domain.Course;

public class CourseAddEditPresenter {
    CourseAddEditView view;
    CourseDAO courseDAO;


    public CourseAddEditPresenter(CourseAddEditView view, CourseDAO courseDAO) {
        this.view = view;
        this.courseDAO = courseDAO;
    }

    public void setUpMode() {
        Integer selectedCourseId = view.getSelectedCourseId();
        if (selectedCourseId == -1) {
            view.setUpAddMode();
        } else {
            Course course = courseDAO.find(selectedCourseId);
            view.setUpEditMode(course);
        }
    }

    public void onClickSaveCourse() {
        if (view.getCourseName().isEmpty() || view.getCourseDescription().isEmpty() ||
                view.getCourseSemester() == -1 || view.getCourseECTS() == -1) {
            view.showEmptyFieldsDetected();
            return;
        }

        Course course = new Course(view.getCourseName(), view.getCourseDescription(), view.getCourseECTS(), view.getCourseSemester());
        if (view.getSelectedCourseId() == -1) {
            courseDAO.save(course);
            view.onSuccessfulAddCourse();
        } else {
            course.setId(view.getSelectedCourseId());
            courseDAO.save(course);
            view.onSuccessfulEditCourse();
        }

    }

    public void onClickDeleteCourse() {
        courseDAO.delete(view.getSelectedCourseId());
        view.onSuccessfulDeleteCourse();
    }
}
