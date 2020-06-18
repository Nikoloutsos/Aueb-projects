package com.example.studytracker.view.course.addEditDeleteCourse;

import com.example.studytracker.domain.Course;

public interface CourseAddEditView {
    void onSuccessfulAddCourse();

    void onSuccessfulEditCourse();

    void onSuccessfulDeleteCourse();

    String getCourseName();

    void setCourseName(String courseName);

    String getCourseDescription();

    void setCourseDescription(String courseDescription);

    int getCourseECTS();

    void setCourseECTS(int courseECTS);

    int getCourseSemester();

    void setCourseSemester(int courseSemester);

    String getSaveButtonText();

    void setSaveButtonText(String buttonText);

    String getToolbarTitle();

    void setToolbarTitle(String title);

    Integer getSelectedCourseId();

    void setSelectedCourseId(Integer courseId);

    void showEmptyFieldsDetected();

    void setUpAddMode();

    void setUpEditMode(Course course);
}
