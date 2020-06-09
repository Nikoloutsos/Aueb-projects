package com.example.studytracker.view.courseRegistration.selectCoursesForCourseRegistration;

import com.example.studytracker.domain.Course;

import java.util.List;

public interface SelectCourseView {
    List<Course> getSelectedCourses();

    void loadAllCourses(List<Course> courseList);

    void onClickAddCoursesInCourseRegistration(List<Course> courseList);

    void showNoCourseSelected();
}
