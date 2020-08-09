package com.api.news.demo.utils;

public class StringUtils {

    public static boolean isStringNullOrEmpty(String s) {
        return s == null || "".equals(s);
    }

    public static boolean isLongNullOrZero(Long l) {
        return l == null || 0L == l;
    }

    public static String escapeStringSQL(String s){
        return s.toLowerCase().trim()
                .replaceAll("%", "\\\\%")
                .replaceAll("_","\\\\_");
    }
}
