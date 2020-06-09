package com.example.studytracker.domain.setterTest;

import com.example.studytracker.domain.CoursesRegistration;
import com.example.studytracker.domain.User;
import com.example.studytracker.util.Cryptography;
import com.example.studytracker.util.RegexUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {

    User user;

    @Before
    public  void init(){
//        user = new User();
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setMail(){
        user.setMail("nick@g");
    }

    @Test
    public void check_successful_setMail(){
        user.setMail("nick@gmail.com");
    }


    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setPassword(){
        user.setEncryptedPassword("");
    }

    @Test
    public void check_successful_setPassword(){
        user.setEncryptedPassword("123");
        Assert.assertEquals(Cryptography.md5("123"), user.getEncryptedPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void check_unsuccessful_setName(){
        user.setName("");
    }

    @Test
    public void check_successful_setName(){
        user.setName("nick");
        Assert.assertEquals("nick", user.getName());
    }


}