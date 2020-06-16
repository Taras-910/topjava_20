package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenDate;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    public static AtomicInteger COUNTER = new AtomicInteger(0);
    public static Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMap;
        if (meal.isNew()) {
            meal.setId(COUNTER.incrementAndGet());
            userMap = repository.containsKey(userId) ? repository.get(userId) : new HashMap<>();
            userMap.put(meal.getId(), meal);
            repository.put(userId, userMap);
            return meal;
            }
        else {
            userMap = checkNotFound(repository.getOrDefault(userId, null), "" + meal.getId());
            userMap.computeIfPresent(meal.getId(), (id, oldMeal) -> {
                if (oldMeal.getUserId() != userId) {
                    checkNotFound(false, "user don't has entity " + meal);
                }
                return meal;
            });
        }
         return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        Map<Integer, Meal> userMap = checkNotFound(repository.getOrDefault(userId, null),""+id);
        checkNotFoundWithId(userMap.get(id).getUserId() == userId, id);
        return userMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get {}", id);
        Map<Integer, Meal> userMap = checkNotFound(repository.getOrDefault(userId, null),""+id);
        Meal meal = userMap.getOrDefault(id, null);
        checkNotFoundWithId(meal.getUserId() == userId, id);
        return checkNotFoundWithId(meal, id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> list = new ArrayList();
        for(Map<Integer,Meal> map : repository.values()) list.addAll(map.values());
        return list;
    }

    @Override
    public List<Meal> getAll(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAll(startDate, endDate,startTime, endTime) {} {} {}", startDate, endDate, userId);
        List<Meal> list = getAll(userId).stream()
                .filter(meal -> isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(toList());
        if (userId == 0) return list;
        else return list.stream()
                .filter(meal -> meal.getUserId() == userId)
                .collect(toList());
    }
}
