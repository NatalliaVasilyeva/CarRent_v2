package com.dmdev.natalliavasilyeva.persistence.utils;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;

public final class DateTimeService {

    private DateTimeService() {
    }

    public static Instant fromStringDateToInstant(String date) {
        if (date == null) {
            return null;
        }
        return Timestamp.valueOf(LocalDateTime.parse(date)).toInstant();
    }
}