package com.youvegotnigel.automation.sample_tests;

import com.github.javafaker.Faker;

import java.util.Locale;

/**
 * This is a Test Class to verify generic methods are working as expected
 * Dec 30, 2022
 *
 * @author Nigel Mulholland
 * @version 1.0
 * @since 1.0
 */
public class FakerDemo {

    public static void main(String[] args) {

        for (int i=0; i<10; i++){
            printDetails();
        }

    }

    public static void printDetails(){

        Faker faker = new Faker();

        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String email = firstName.toLowerCase(Locale.ROOT)+"."+lastName.toLowerCase(Locale.ROOT)+"@gmail.com";
        String phoneNo = faker.phoneNumber().cellPhone();
        String streetAddress = faker.address().streetAddress();
        String city = faker.country().capital();
        String description = faker.chuckNorris().fact();
        String zipCode = faker.code().asin();

        System.out.println("First Name   : " + firstName);
        System.out.println("Last Name    : " + lastName);
        System.out.println("Email        : " + email);
        System.out.println("Phone Number : " + phoneNo);
        System.out.println("Address      : " + streetAddress);
        System.out.println("City         : " + city);
        System.out.println("City         : " + zipCode);
        System.out.println("Description  : " + description);
        System.out.println("--------------------------------------------------\n");
    }
}
