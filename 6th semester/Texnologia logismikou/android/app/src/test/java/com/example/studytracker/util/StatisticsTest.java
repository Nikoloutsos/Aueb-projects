//package com.example.studytracker.util;
//
//import com.example.studytracker.domain.Course;
//import com.example.studytracker.domain.RegisteredCourse;
//import com.example.studytracker.domain.Student;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.mockito.Mock;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoRule;
//
//import java.util.ArrayList;
//import java.util.List;
//import static org.mockito.Mockito.*;
//
//
//public class StatisticsTest {
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//    @Mock
//    Student student;
//
//    @Mock
//    RegisteredCourseDAOMemory dao;
//
//
//    @Before
//    public void init(){
//        when(student.getNoSemesters()).thenReturn(8);
//        List<RegisteredCourse> list = new ArrayList<>();
//        for (int i = 0; i < 6 ; i++) {
//            list.add(mock(RegisteredCourse.class));
//            when(list.get(i).getGrade()).thenReturn(6.0);
//
//            Course mock = mock(Course.class);
//            when(mock.getSemester()).thenReturn(2);
//            when(mock.getEcts()).thenReturn(10);
//            when(list.get(i).getCourse()).thenReturn(mock);
//        }
//        when(dao.getAllRegisteredCourses()).thenReturn(list);
//    }
//
//    @Test
//    public void calculateAveragePerSemester() {
//        Statistics statistics = new Statistics(student, dao);
//        double[] result = statistics.calculateAveragePerSemester();
//
//        double[] expected = {0 , 0, 6, 0, 0, 0, 0, 0, 0};
//        for (int i = 0; i < student.getNoSemesters() ; i++) {
//            Assert.assertTrue(expected[i] == result[i]);
//        }
//    }
//
//    @Test
//    public void calculateECTSPerSemester() {
//        Statistics statistics = new Statistics(student, dao);
//        double[] result = statistics.calculateECTSPerSemester();
//
//        double[] expected = {0 , 0, 60, 0, 0, 0, 0, 0, 0};
//        for (int i = 0; i < student.getNoSemesters() ; i++) {
//            Assert.assertTrue(expected[i] == result[i]);
//        }
//    }
//}