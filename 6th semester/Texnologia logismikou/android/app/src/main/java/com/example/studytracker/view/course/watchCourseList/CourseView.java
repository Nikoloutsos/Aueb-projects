package com.example.studytracker.view.course.watchCourseList;

import com.example.studytracker.domain.Course;

import java.util.List;

public interface CourseView {
    void onFetchCourses(List<Course> courseList);

    void onAddCourse();
}
