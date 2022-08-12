package com.dmdev.natalliavasilyeva.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class DateTimeService {
    private static final DateTimeFormatter FORMATTER_FULL = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private static final DateTimeFormatter FORMATTER_SHORT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private DateTimeService() {
    }

    public static Instant fromStringDateToInstant(String date) {
        if (date == null) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.parse(date)).toInstant();
    }

    public static LocalDateTime fromStringLocalDateTime(String date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.parse(date, FORMATTER_SHORT);
    }

    public static String formatDate(LocalDateTime date) {
        return date.format(FORMATTER_FULL);
    }

    public static Instant fromLocalDateTimeDateToInstant(LocalDateTime date) {
        if (date == null) {
            return null;
        }
        return date.toInstant(ZoneOffset.UTC);
    }
}