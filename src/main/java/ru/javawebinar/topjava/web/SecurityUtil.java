package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Component;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

@Component
public class SecurityUtil {

    public static int authId = 0;

    public static int getAuthId() {
        return authId;
    }

    public static void setAuthId(int id) {
        authId = id;
    }

    public static int authUserId() {
        return getAuthId();
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }
}
