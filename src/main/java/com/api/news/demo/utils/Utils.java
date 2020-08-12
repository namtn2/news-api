package com.api.news.demo.utils;

import java.util.Date;

public class Utils {

    public static boolean isStringNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isLongNullOrZero(Long l) {
        return l == null || 0L == l;
    }

    public static String escapeStringSQL(String s) {
        return s.toLowerCase().trim()
                .replaceAll("%", "\\\\%")
                .replaceAll("_", "\\\\_");
    }

    public static long diffDateGetMinutes(Date dBefore, Date dAfter) {
        return (dAfter.getTime() - dBefore.getTime()) / (1000 * 60);
    }
}
