package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {

    public static int authUserId() {
        return 1;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

    public String getAuthUserId() {
        return "1";
    }
}