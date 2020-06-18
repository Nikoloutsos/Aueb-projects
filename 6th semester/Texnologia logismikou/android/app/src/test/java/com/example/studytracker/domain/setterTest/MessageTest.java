package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.Message;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageTest {

    Message message;

    @Before
    public  void init(){
        message = new Message();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setTitle(){
        message.setTitle("");
    }

    @Test
    public void check_successful_setTitle(){
        message.setTitle("title");
        Assert.assertEquals("title", message.getTitle());
    }


    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setDescription(){
        message.setDescription("");
    }

    @Test
    public void check_successful_setDescription(){
        message.setDescription("description");
        Assert.assertEquals("description", message.getDescription());
    }
}