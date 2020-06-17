package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);
    public static AtomicInteger COUNTER = new AtomicInteger(0);
    public static Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getDate().getDayOfMonth() == 30 ? 1 : 2));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        log.info("save");
        Map<Integer, Meal> userMap;
        if (meal.isNew()) {
            log.info("create meal userId {} {}",meal,userId);
            meal.setId(COUNTER.incrementAndGet());
            userMap = repository.containsKey(userId) ? repository.get(userId) : new HashMap<>();
            userMap.put(meal.getId(), meal);
            repository.put(userId, userMap);
            return meal;
        } else {
            log.info("update meal userId {} {}",meal,userId);
            return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete {}", id);
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get id {} userId {}", id, userId);
        Meal falseMeal = new Meal (null, null, null,0);
        Optional<Map<Integer,Meal>> mapOptional = Optional.ofNullable(repository.get(userId));
        if (mapOptional.isPresent()) {
            Optional<Meal> mealOptional = Optional.ofNullable(mapOptional.get().get(id));
            if(mealOptional.isPresent()){
                return mealOptional.get();
            }
        }
        return falseMeal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        log.info("getAll userId {}",userId);
        return repository.get(userId).values().stream()
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(toList());
    }

    @Override
    public Collection<Meal> getAllFilteredByDate(LocalDate startDate, LocalDate endDate, int userId) {
        log.info("getAllFilteredByDate(startDate, endDate,startTime, endTime) {} {} {}", startDate, endDate, userId);
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .collect(toList());
    }
}
