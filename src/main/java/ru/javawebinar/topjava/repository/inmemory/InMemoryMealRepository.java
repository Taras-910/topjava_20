package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenDate;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final static Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    public static Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    public static AtomicInteger COUNTER = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(COUNTER.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        //.replace(meal.getId(), meal) == null ? null : meal; ??
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id) {
        log.info("get {}", id);
        return repository.getOrDefault(id, null);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(toList());
    }

    @Override
    public Collection<Meal> getAll(String startDate, String endDate, int authUserId) {
        log.info("getAll(startDate, endDate,startTime, endTime) {} {} {}", startDate, endDate, authUserId);

        List<Meal> list = getAll().stream().filter(meal -> isBetweenDate(meal.getDate(),
                startDate == null || startDate.isEmpty() ? LocalDate.MIN : LocalDate.parse(startDate),
                endDate == null || endDate.isEmpty() ? LocalDate.MAX : LocalDate.parse(endDate)))
                .collect(toList());

        if (authUserId == 0) return list;
        else return list.stream().filter(meal -> meal.getUserId() == authUserId).collect(toList());
    }
}
