package com.youvegotnigel.automation.sample_tests;

import java.util.Base64;

/**
 * This is a Test Class to verify generic methods are working as expected
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class TextEncryptor {

    public static void main(String[] args) {

        String sample = "test@123";

        System.out.println("Sample String: " + sample);
        System.out.println("Encoded String: " + encodeText(sample));
        System.out.println("Actual String: " + decodeText(encodeText(sample)));

    }

    /**
     *
     * @param text actual value which needs to be encrypted
     * @return encrypted value of input parameter
     */
    public static String encodeText(String text){
        return Base64.getEncoder().encodeToString(text.getBytes());
    }

    /**
     *
     * @param text encrypted value
     * @return actual value
     */
    public static String decodeText(String text){
        byte[] actualByte = Base64.getDecoder().decode(text);
        String actualString = new String(actualByte);
        return actualString;
    }

}
