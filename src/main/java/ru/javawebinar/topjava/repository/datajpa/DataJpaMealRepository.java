package ru.javawebinar.topjava.repository.datajpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Profile("datajpa")
public class DataJpaMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger("");
    private final CrudMealRepository mealRepository;
    private final CrudUserRepository userRepository;

    public DataJpaMealRepository(CrudMealRepository mealRepository, CrudUserRepository userRepository, CrudUserRepository crudUserRepository) {
        this.mealRepository = mealRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(userRepository.getOne(userId));
        return meal.isNew() || get(meal.id(), userId) != null ? mealRepository.save(meal) : null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return mealRepository.delete(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = mealRepository.findById(id).orElse(null);
        return meal != null && meal.getUser().getId() == userId ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return mealRepository.findByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startTime, LocalDateTime endTime, int userId) {
//  1-variant
        return mealRepository.findByUserIdAndDateTimeGreaterThanEqualAndDateTimeLessThanOrderByDateTimeDesc(userId, startTime, endTime);
//  2-variant
        /*return repo.getBetweenHalfOpen(userId, startTime, endTime);*/
    }

    @Transactional(readOnly = true)
    @Override
    public Meal getWithUser(int id, int userId) {
        Meal actual = get(id, userId);
        User user = userRepository.findById(userId).orElse(null);
        if (actual == null || user == null) return null;
        actual.setUser(user);
        return actual;
    }
}
