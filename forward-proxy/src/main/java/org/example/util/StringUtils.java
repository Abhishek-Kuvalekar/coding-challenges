package org.example.util;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        return (str == null || str.isEmpty());
    }

    public static boolean isNull(String str) {
        return (str == null);
    }

    public static boolean isEmpty(String str) {
        if (isNull(str)) return false;
        return str.isEmpty();
    }
}
