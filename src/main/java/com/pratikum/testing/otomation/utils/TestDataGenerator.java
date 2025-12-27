package com.pratikum.testing.otomation.utils;

import java.util.Random;
import java.util.UUID;

public class TestDataGenerator {

    private static final Random RANDOM = new Random();

    private static final String[] FIRST_NAMES = {
            "John", "Jane", "Michael", "Sarah",
            "David", "Lisa", "Robert", "Maria"
    };

    private static final String[] LAST_NAMES = {
            "Smith", "Johnson", "Williams", "Brown",
            "Jones", "Garcia", "Miller", "Davis"
    };

    // =========================
    // FIX UNTUK PAYMENT TEST
    // =========================

    /**
     * Menghasilkan nama lengkap acak.
     * Dipanggil oleh PaymentProcessTest baris 64 & 103
     */
    public static String generateRandomName() {
        return validFirstName() + " " + validLastName();
    }

    /**
     * Menghasilkan nomor kartu kredit acak (16 digit).
     * Dipanggil oleh PaymentProcessTest baris 67 & 104
     */
    public static String generateRandomCardNumber() {
        return randomNumericString(16);
    }

    // =========================
    // POSITIVE DATA
    // =========================
    public static String validFirstName() {
        return FIRST_NAMES[RANDOM.nextInt(FIRST_NAMES.length)];
    }

    public static String validLastName() {
        return LAST_NAMES[RANDOM.nextInt(LAST_NAMES.length)];
    }

    public static String validEmail() {
        return "user_" + UUID.randomUUID().toString().substring(0, 8) + "@mail.com";
    }

    public static String validPassword() {
        return "Qa@" + RANDOM.nextInt(9999) + "Test";
    }

    // =========================
    // NEGATIVE DATA (Dan method lainnya tetap sama...)
    // =========================
    public static String emptyValue() {
        return "";
    }

    // ... (Method invalidEmail, weakPassword, dll tetap dipertahankan di bawah)

    // =========================
    // UTILS
    // =========================
    public static String randomNumericString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM.nextInt(10));
        }
        return sb.toString();
    }

    public static String randomAlphaNumericString(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(RANDOM.nextInt(chars.length())));
        }
        return sb.toString();
    }
}