package ru.javawebinar.topjava.repository.repositoryFromMemory;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.RepositoryInterface;
import ru.javawebinar.topjava.web.MealServlet;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;

public class InMemory implements RepositoryInterface {
    private static final Logger log = getLogger(MealServlet.class);

    private Map<Integer, Meal> mealsMap = new ConcurrentHashMap();
    public static AtomicInteger counter = new AtomicInteger(1);

    private List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public InMemory(){
        log.debug("meals {}", meals.size());
        meals.forEach(meal -> {
            int id = counter.getAndIncrement();
            meal.setId(id);
            mealsMap.put(id, meal);
        });
    }


    @Override
    public void save(Meal meal, int mealId) {
        if(mealId == 0) meal.setId(counter.getAndIncrement());
        mealsMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int mealId) {
        mealsMap.remove(mealId);
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> list = new ArrayList<>();
        for(Map.Entry m : mealsMap.entrySet()){
            list.add((Meal) m.getValue());
        }
        return list;
    }

    @Override
    public Meal getMealById(int mealId) { return mealsMap.get(mealId); }
}
