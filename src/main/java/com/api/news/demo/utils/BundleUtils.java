package com.api.news.demo.utils;

import lombok.extern.log4j.Log4j;

import java.util.Locale;
import java.util.ResourceBundle;

@Log4j
public class BundleUtils {

    private static final Locale locale = Locale.getDefault();
    private static final String languageFile = "language/language_vi";

    public static String getLanguage(String code) {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle(languageFile, locale);
            if (resourceBundle != null) {
                return resourceBundle.getString(code);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
