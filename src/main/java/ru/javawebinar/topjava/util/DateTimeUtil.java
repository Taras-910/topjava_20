package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil <T>{
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static <T extends Comparable>boolean isBetween(T obj, T startDate, T endDate) {
        return obj.getClass().equals(LocalDate.class) ?
                obj.compareTo(startDate) >= 0 && obj.compareTo(endDate) <= 0 :
                obj.compareTo(startDate) >= 0 && obj.compareTo(endDate) < 0;
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
