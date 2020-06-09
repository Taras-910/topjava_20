package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.time.LocalTime;
import java.util.List;

public class To {
    public List<MealTo> createMealTo(List<Meal> meals){
        return MealsUtil.filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
