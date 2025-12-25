package com.pratikum.testing.otomation.utils;

import java.util.Random;

/**
 * Utility class untuk generate test data
 */

public class TestDataGenerator {
    private static final String[] FIRST_NAMES = {"John", "Jane",
            "Michael", "Sarah", "David", "Lisa", "Robert", "Maria"};
    private static final String[] LAST_NAMES = {"Smith", "Johnson",
            "Williams", "Brown", "Jones", "Garcia", "Miller", "Davis"};
    private static final Random random = new Random();

    // Methods untuk generate random email
    public static String generateRandomEmail() {
        return "testuser" + System.currentTimeMillis() +
                random.nextInt(1000) + "@test.com";
    }

    // Methods untuk generate random first name
    public static String generateRandomFirstName() {
        return FIRST_NAMES[random.nextInt(FIRST_NAMES.length)] ;
    }

    // Methods untuk generate random last name
    public static String generateRandomLastName() {
        return LAST_NAMES[random.nextInt(LAST_NAMES.length)] ;
    }

    // Methods untuk generate random password
    public static String generateRandomPassword() {
        return "Test@" + System.currentTimeMillis();
    }

    // Methods untuk generate random weak password
    public static String generateWeakPassword() {
        return "123";
    }

    // Methods untuk generate special characters name
    public static String generateNameWithSpecialChars() {
        return "Test@#$%User";
    }

    // Methods untuk generate long name (boundary testing)
    public static String generateLongName() {
        return "A".repeat(100); // 100 karakter
    }

    // Method untuk generate invalid email
    public static String generateInvalidEmail() {
        return "invalidemail";
    }
}