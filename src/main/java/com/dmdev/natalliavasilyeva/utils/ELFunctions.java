package com.dmdev.natalliavasilyeva.utils;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ELFunctions {

    private ELFunctions() {
        //
    }

    public static String formatLocalDateTime(LocalDateTime localDateTime, String pattern) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

}