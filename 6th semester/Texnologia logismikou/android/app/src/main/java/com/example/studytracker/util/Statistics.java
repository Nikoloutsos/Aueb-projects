package com.example.studytracker.util;

import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.RegisteredCourse;

import java.util.HashMap;
import java.util.List;

/**
 * Class containing static method for calculating statistics
 *
 * @author Konstantinos Nikoloutsos
 * @author Jenny Bolena
 */
public class Statistics {

    /**
     * Calculate and returns the mean score value for every month
     *
     * @param allCourseRegistrations Student's course registrations
     * @return A hashmap containing the mean value score for every semester.
     * (Semester is the key and mean score is the value)
     */
    public static HashMap<Integer, Double> calculateMeanValues(List<CoursesRegistration> allCourseRegistrations) {
        HashMap<Integer, Double> sumHashmap = new HashMap<>();
        HashMap<Integer, Integer> countHashMap = new HashMap<>();

        for (CoursesRegistration allCourseRegistration : allCourseRegistrations) {
            for (RegisteredCourse registeredCours : allCourseRegistration.getRegisteredCourses()) {
                if (registeredCours.getGrade() == -1) continue;
                int semester = registeredCours.getCourse().getSemester();
                if (!sumHashmap.containsKey(semester)) {
                    sumHashmap.put(semester, 0d);
                    countHashMap.put(semester, 0);
                }

                Double sum = sumHashmap.get(semester);
                sum += registeredCours.getGrade();
                sumHashmap.put(semester, sum);

                Integer countForThisSemester = countHashMap.get(semester);
                countForThisSemester++;
                countHashMap.put(semester, countForThisSemester);
            }
        }

        HashMap<Integer, Double> result = new HashMap<>();
        for (Integer integer : sumHashmap.keySet()) {
            Double mean = sumHashmap.get(integer) / countHashMap.get(integer);
            result.put(integer, mean);
        }
        return result;
    }

    /**
     * Calculates and returns the mean score
     *
     * @param allCourseRegistrations Student's course registrations
     * @return The mean score
     */
    public static Double calculateCurrentMeanScore(List<CoursesRegistration> allCourseRegistrations) {
        Double sum = 0d;
        Integer count = 0;
        for (CoursesRegistration allCourseRegistration : allCourseRegistrations) {
            for (RegisteredCourse registeredCours : allCourseRegistration.getRegisteredCourses()) {
                if (registeredCours.getGrade() == -1) continue;
                sum += registeredCours.getGrade();
                count++;
            }
        }

        if (count == 0) return 0d;
        return sum / count;
    }

    /**
     * Calculates and returns the total ECTS
     *
     * @param allCourseRegistrations Student's course registrations
     * @return The total ECTS
     */
    public static Integer calcualteTotalEcts(List<CoursesRegistration> allCourseRegistrations) {
        Integer sum = 0;
        for (CoursesRegistration allCourseRegistration : allCourseRegistrations) {
            for (RegisteredCourse registeredCours : allCourseRegistration.getRegisteredCourses()) {
                if (registeredCours.getGrade() == -1) continue;
                sum += registeredCours.getCourse().getEcts();
            }
        }

        return sum;
    }
}
