package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public MealService() {}

    public void create(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal, userId).getUserId() == userId, "meal = "+meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(get(id, userId).getUserId() == userId, id);
        checkNotFoundWithId(repository.delete(id, authUserId()), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        checkNotFoundWithId(meal.getUserId() == userId, id);
        return meal;
    }

    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, int userId) {
        return (List<Meal>) repository.getAll(startDate, endDate, userId);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

}

