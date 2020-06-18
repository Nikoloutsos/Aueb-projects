package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.AcademicPeriod;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AcademicPeriodTest {

    AcademicPeriod academicPeriod;

    @Before
    public  void init(){
         academicPeriod = new AcademicPeriod();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setAcademicSemesterType(){
        academicPeriod.setAcademicSemesterType(5);
    }

    @Test
    public void check_successful_setAcademicSemesterType(){
        academicPeriod.setAcademicSemesterType(0);
        Assert.assertEquals(0, academicPeriod.getAcademicSemesterType());
    }



}