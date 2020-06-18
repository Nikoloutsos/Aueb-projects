package com.example.studytracker.domain;

import androidx.annotation.NonNull;

public class AcademicPeriod {

    public static final int SPRING = 0;
    public static final int AUTUMN = 1;

    private int startYear;
    private int academicSemesterType;

    public AcademicPeriod() {
    }

    public AcademicPeriod(int startYear, int academicSemester) {
        this.startYear = startYear;
        setAcademicSemesterType(academicSemester);
    }

    /**
     * Getters and setters
     */

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getAcademicSemesterType() {
        return academicSemesterType;
    }

    public void setAcademicSemesterType(int academicSemester) {
        if ((academicSemester == SPRING) || (academicSemester == AUTUMN)) {
            this.academicSemesterType = academicSemester;
            return;
        }
        throw new IllegalArgumentException();
    }

    public String getAcademicPeriodString() {
        return academicSemesterType == 0 ? "Spring" : "Autumn";
    }

    @NonNull
    @Override
    public String toString() {
        return startYear + " " + getAcademicPeriodString();
    }
}
