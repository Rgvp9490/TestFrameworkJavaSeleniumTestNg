package framework.utils;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class UtilsData {

    private static final Faker faker = new Faker(new Locale("en-IND"));
    private static final Random random = new Random();

    public static void main(String[] args) {
        System.out.println("First Name: " + firstName());
        System.out.println("Email: " + email());
        System.out.println("Random AlphaNumeric: " + randomAlphaNumeric(10));
        System.out.println("Current Date Formatted: " + getTodayDateFormatted("dd-MM-yyyy"));
        System.out.println("Timestamp: " + timeStamp());
    }

    public static int getTodaysDate() {
        return LocalDate.now().getDayOfMonth();
    }

    public static String getCurrentMonth() {
        return LocalDate.now().getMonth().name(); // e.g., "MAY"
    }

    public static int getCurrentMonthNumber() {
        return LocalDate.now().getMonthValue(); // 1-12
    }

    public static String getCurrentYear() {
        return String.valueOf(LocalDate.now().getYear());
    }

    public static String getTodayDateFormatted(String format) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalDate.now().format(formatter);
    }

    public static String randomAlphaNumeric(int length) {
        return generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789", length);
    }

    public static String randomAlphabetic(int length) {
        return generateRandomChars("ABCDEFGHIJKLMNOPQRSTUVWXYZ", length);
    }

    public static String randomNumeric(int length) {
        return generateRandomChars("0123456789", length);
    }

    private static String generateRandomChars(String characters, int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(characters.charAt(random.nextInt(characters.length())));
        }
        return sb.toString();
    }

    public static String email() {
        return faker.internet().safeEmailAddress(); // ✅ Valid in net.datafaker
    }

    public static String firstName() {
        return faker.name().firstName();
    }

    public static String lastName() {
        return faker.name().lastName();
    }

    public static String timeStamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        return LocalDateTime.now().format(formatter);
    }
}