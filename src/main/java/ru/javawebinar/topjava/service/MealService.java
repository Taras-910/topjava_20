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

    public void create(Meal meal, int userId) {
        repository.save(meal, userId);
    }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal, userId), "id = "+meal.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, authUserId()), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id, userId);
        checkNotFoundWithId(meal.getId(), id);
        return meal;
    }

    public List<Meal> getAllFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        return (List<Meal>) repository.getAllFilteredByDate(startDate, endDate, userId);
    }

    public List<Meal> getAll(int userId) {
        return (List<Meal>) repository.getAll(userId);
    }

}

