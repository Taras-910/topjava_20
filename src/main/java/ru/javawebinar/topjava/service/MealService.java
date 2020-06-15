package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    @Autowired
    private MealRepository repository;

    public MealService() {
        this.repository = new InMemoryMealRepository();
    }

    public void create(Meal meal) { repository.save(meal); }

    public void update(Meal meal, int userId) {
        checkNotFound(repository.save(meal).getUserId() == userId, "meal = "+meal);
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(get(id, userId).getUserId() == userId, id);
        checkNotFoundWithId(repository.delete(id), id);
    }

    public Meal get(int id, int userId) {
        Meal meal = repository.get(id);
        checkNotFoundWithId(meal.getUserId() == userId, id);
        return meal;
    }

    public Collection<Meal> getAll(String startDate, String endDate, int authUserId) {
        return repository.getAll(startDate, endDate, authUserId);
    }
}

