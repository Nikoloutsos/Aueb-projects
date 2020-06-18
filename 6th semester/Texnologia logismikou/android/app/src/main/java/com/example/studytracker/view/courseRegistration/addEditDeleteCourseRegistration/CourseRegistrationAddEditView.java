package com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration;

import com.example.studytracker.domain.Course;

import java.util.List;

public interface CourseRegistrationAddEditView {

    String getMode();

    Integer getAcademicPeriodYear();

    void setAcademicPeriodYear(int year);

    String getAcademicPeriodType();

    void setAcademicPeriodType(String periodType);

    String getDescription();

    void setDescription(String descriptionText);

    String getSaveButtonText();

    void setSaveButtonText(String buttonText);

    Integer getIdForCourseRegistration();

    List<Course> getSelectedRegisteredCourses();

    void setSelectedCourses(String selectedCourses);

    void setToolbarTitle(String title);

    void hideDeleteButton();

    void showDeleteButton();


    void onClickAddCourse();

    void onClickDeleteCourse();


    void showCourseRegistrationSaved();

    void showCourseRegistrationEdited();

    void showCourseRegistrationDeleted();

    void showEmptyDescription();
}
