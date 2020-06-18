package com.example.studytracker.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <p>Class containing static methods for crypt and decrypt Strings</p>
 */
public class Cryptography {

    public static String md5(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(message.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

    }
}
