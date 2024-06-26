package com.example.fyp_application.Utils;


import java.security.SecureRandom;

public class InformationGeneratorUtils {


    //define the final string of characters to be used in the password
    //This class is just meant to generate random information to quickly populate the database
    //It is not meant to be used in a production environment
    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String OTHER_CHAR = "!@$";


    private static final String SAMPLE_DOMAIN = "testing.com";
    private static final String PASSWORD_ALLOW = CHAR_LOWER + CHAR_UPPER + NUMBER + OTHER_CHAR;

    private static final String EMAIL_ALLOW = CHAR_LOWER + CHAR_UPPER + NUMBER;

    private static final String SERIAL_NUMBER = CHAR_UPPER + NUMBER;

    private static final SecureRandom random = new SecureRandom();

    private InformationGeneratorUtils(){}
    public static String generatePassword(int length) {
        if (length < 4) {
            throw new IllegalArgumentException();
        }

        StringBuilder password = new StringBuilder(length);

        // build the password
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(PASSWORD_ALLOW.length());
            password.append(PASSWORD_ALLOW.charAt(index));
        }

        return new String(password);
    }


    public static String generateUsername(String firstName, String lastName) {

        SecureRandom random = new SecureRandom();


        char firstNameInitialChar = firstName.charAt(0);
        char lastNameInitialCar = lastName.charAt(0);

        int randomInt = 1000 + random.nextInt(9000);

        int randomIndex = random.nextInt(lastName.length());
        char randomLastNameChar = lastName.charAt(randomIndex);


        return (String.valueOf(firstNameInitialChar) + String.valueOf(lastNameInitialCar) + randomInt + randomLastNameChar).toLowerCase();
    }


    public static String generateUserEmail(String firstName, String lastName) {

        String domain = "testing.com";

        return (firstName + "." + lastName + "@" + domain).toLowerCase();
    }

    public static String generateSupplierEmail(String supplierName) {


        return (supplierName + "@" + SAMPLE_DOMAIN).toLowerCase();
    }


    public static String generateSerialNumber(int length) {

        if (length < 8) {
            throw new IllegalArgumentException();
        }

        StringBuilder serialNumber = new StringBuilder(length);

        // build the serial number
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(SERIAL_NUMBER.length());
            serialNumber.append(SERIAL_NUMBER.charAt(index));
        }

        return new String(serialNumber);
    }


}

