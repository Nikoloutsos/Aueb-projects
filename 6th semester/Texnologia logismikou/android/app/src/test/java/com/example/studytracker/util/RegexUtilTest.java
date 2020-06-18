package com.example.studytracker.util;

import org.junit.Assert;
import org.junit.Test;

public class RegexUtilTest {
    @Test
    public void check_for_valid_email() {
        String email = "zafeiris@aueb.gr";
        Assert.assertTrue(RegexUtil.isEmail(email));
    }

    @Test
    public void check_for_invalid_email() {
        String email = "dsafewreg@";
        Assert.assertFalse(RegexUtil.isEmail(email));
    }
}