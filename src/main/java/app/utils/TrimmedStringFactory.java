package app.utils;

public class TrimmedStringFactory {

    private static final String TRIPLEDOT = "...";

    public static String trimString(String string, int maxLength) {
        if (string.length() > maxLength && maxLength > 3) {
            string = string.substring(0, maxLength - TRIPLEDOT.length());
            string = string + TRIPLEDOT;
        }
        return string;
    }

}
