package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface RepositoryInterface {

    void save(Meal meal, int mealId);

    void delete(int mealId);

    List<Meal> getAll();

    Meal getMealById(int mealId);
}
