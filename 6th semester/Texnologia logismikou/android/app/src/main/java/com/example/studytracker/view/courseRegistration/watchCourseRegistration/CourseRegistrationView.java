package com.example.studytracker.view.courseRegistration.watchCourseRegistration;

import com.example.studytracker.domain.CoursesRegistration;

import java.util.List;

public interface CourseRegistrationView {
    void loadAllCourseRegistration(List<CoursesRegistration> coursesRegistrationList);

    void onAddCourseRegistration();
}
