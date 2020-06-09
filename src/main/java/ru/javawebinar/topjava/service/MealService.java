package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.RepositoryInterface;
import ru.javawebinar.topjava.repository.repositoryFromMemory.InMemory;

import java.util.List;

public class MealService {

    private RepositoryInterface repository;

    public MealService() {
        repository = new InMemory();
    }

    public void add(Meal meal) {
        repository.save(meal, 0);
    }

    public void delete(int mealId) {
        repository.delete(mealId);
    }

    public void update(Meal meal, int mealId) {
        repository.save(meal, mealId);
    }

    public List<Meal> getAll() {
        return repository.getAll();
    }

    public Meal getMealById(int mealId) {
        return repository.getMealById(mealId);
    }
}


