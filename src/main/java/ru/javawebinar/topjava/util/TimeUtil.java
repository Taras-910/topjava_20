package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) < 0;
    }

    public static LocalDate mealDate(UserMeal meal){
        return meal.getDateTime().toLocalDate();
    }

    public  static LocalTime mealTime(UserMeal meal){
        return meal.getDateTime().toLocalTime();
    }
}
