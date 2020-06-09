package com.example.studytracker.util;

import com.example.studytracker.util.Cryptography;

import org.junit.Assert;
import org.junit.Test;

public class CryptographyTest {

    @Test
    public void md5() {
        String result = Cryptography.md5("test");
        Assert.assertEquals(result, "98f6bcd4621d373cade4e832627b4f6");
    }

}