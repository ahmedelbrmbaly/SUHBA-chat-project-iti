package com.suhba.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hashing {
    public String doHashing (String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA"); //Or MD5
        messageDigest.update(password.getBytes());
        byte[] resultBytes = messageDigest.digest();
        StringBuilder resultPassword = new StringBuilder();
        for (byte ch: resultBytes)  resultPassword.append(String.format("%02X", ch)); //%02X --> hexa decimal format
        return resultPassword.toString();
    }
}
