package com.example.studytracker.view.statistic;

import com.example.studytracker.dao.daoStub.CourseRegistrationDAO;
import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.util.Statistics;

import java.util.HashMap;
import java.util.List;

public class StatisticsPresenter {
    StatisticsView view;
    CourseRegistrationDAO courseRegistrationDAO;

    public StatisticsPresenter(StatisticsView view, CourseRegistrationDAO courseRegistrationDAO) {
        this.view = view;
        this.courseRegistrationDAO = courseRegistrationDAO;
    }

    void updateStatistics() {
        List<CoursesRegistration> allCourseRegistrations = courseRegistrationDAO.findAll();
        HashMap<Integer, Double> integerDoubleHashMap = Statistics.calculateMeanValues(allCourseRegistrations);
        String meanValuesString = "";
        for (Integer integer : integerDoubleHashMap.keySet()) {
            meanValuesString += integer + " -> " + integerDoubleHashMap.get(integer) + "\n";
        }
        Double totalMeanScore = Statistics.calculateCurrentMeanScore(allCourseRegistrations);
        Integer totalEcts = Statistics.calcualteTotalEcts(allCourseRegistrations);


        view.setMeanScorePerSemester(meanValuesString);
        view.setMeanScore(totalMeanScore);
        view.setTotalEcts(totalEcts);
    }
}
