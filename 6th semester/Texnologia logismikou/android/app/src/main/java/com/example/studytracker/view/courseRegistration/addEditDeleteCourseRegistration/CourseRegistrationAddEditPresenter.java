package com.example.studytracker.view.courseRegistration.addEditDeleteCourseRegistration;

import com.example.studytracker.dao.daoStub.CourseRegistrationDAO;
import com.example.studytracker.domain.AcademicPeriod;
import com.example.studytracker.domain.Course;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.RegisteredCourse;

import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationAddEditPresenter {
    CourseRegistrationAddEditView view;
    CourseRegistrationDAO dao;

    public CourseRegistrationAddEditPresenter(CourseRegistrationAddEditView view, CourseRegistrationDAO dao) {
        this.view = view;
        this.dao = dao;
    }

    public void setUpView() {
        if (view.getMode().equalsIgnoreCase("MODE_ADD")) {
            view.setToolbarTitle("Add a course registration");
            view.setSaveButtonText("Add a course registration");
            view.hideDeleteButton();
        } else if (view.getMode().equalsIgnoreCase("MODE_EDIT")) {
            view.setToolbarTitle("Edit course registration");
            view.setSaveButtonText("Edit course registration");
            view.showDeleteButton();

            Integer courseRegistrationId = view.getIdForCourseRegistration();
            CoursesRegistration coursesRegistration = dao.find(courseRegistrationId);

            view.setDescription(coursesRegistration.getTitle());
            view.setAcademicPeriodYear(coursesRegistration.getAcademicPeriod().getStartYear());
            view.setAcademicPeriodType(coursesRegistration.getAcademicPeriod().getAcademicPeriodString());

            List<RegisteredCourse> registeredCourses = coursesRegistration.getRegisteredCourses();
            String str = "";
            for (RegisteredCourse course : registeredCourses) {
                str += "- " + course.getCourse().getName() + "\n";
            }
            view.setSelectedCourses(str);
        }
    }


    public void onClickSaveCourseRegistration() {
        Integer academicPeriodYear = view.getAcademicPeriodYear();
        String academicPeriodType = view.getAcademicPeriodType();
        String description = view.getDescription();

        if (description.isEmpty()) {
            view.showEmptyDescription();
            return;
        }

        int academicSemesterInt = academicPeriodType.equalsIgnoreCase("Spring") ? 0 : 1;

        AcademicPeriod academicPeriod = new AcademicPeriod(academicPeriodYear, academicSemesterInt);
        List<RegisteredCourse> registeredCourses = new ArrayList<>();
        for (Course course : view.getSelectedRegisteredCourses()) {
            registeredCourses.add(new RegisteredCourse(course));
        }
        CoursesRegistration coursesRegistration = new CoursesRegistration(description, academicPeriod, registeredCourses);
        if (view.getIdForCourseRegistration() != null)
            coursesRegistration.setId(view.getIdForCourseRegistration());

        dao.save(coursesRegistration);

        if (view.getIdForCourseRegistration() == null) {
            view.showCourseRegistrationSaved();
        } else {
            view.showCourseRegistrationEdited();
        }
    }

    public void onClickAddCourses() {
        view.onClickAddCourse();
    }

    public void onClickDeleteCourseRegistration() {
        Integer courseRegistrationId = view.getIdForCourseRegistration();
        dao.delete(courseRegistrationId);
        view.showCourseRegistrationDeleted();
    }
}
