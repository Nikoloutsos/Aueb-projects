package com.example.studytracker.view.course.addEditDeleteCourse;

import com.example.studytracker.dao.daoStub.CourseDAO;
import com.example.studytracker.domain.Course;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseAddEditPresenterTest {

    private CourseAddEditView courseAddEditView;
    private CourseDAO courseDAO;
    private CourseAddEditPresenter presenterUnderTest;

    @Before
    public void setUp() throws Exception {
        courseAddEditView = mock(CourseAddEditView.class);
        courseDAO = mock(CourseDAO.class);
        presenterUnderTest = new CourseAddEditPresenter(courseAddEditView, courseDAO);
    }

    @Test
    public void shouldCallSetUpAddMode() {
        when(courseAddEditView.getSelectedCourseId()).thenReturn(-1);
        presenterUnderTest.setUpMode();
        verify(courseAddEditView).setUpAddMode();
    }

    @Test
    public void shouldCallSetUpEditMode() {
        Course course = new Course("test", "test", 1, 1);
        course.setId(1);
        when(courseDAO.find(1)).thenReturn(course);
        when(courseAddEditView.getSelectedCourseId()).thenReturn(1);
        presenterUnderTest.setUpMode();
        verify(courseAddEditView).setUpEditMode(course);
    }

    @Test
    public void onClickSaveCourse() {

    }

    @Test
    public void onClickDeleteCourse() {
    }
}