package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.To.MealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;
import java.util.function.Predicate;

import static java.time.LocalTime.*;
import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenTimeHalfOpen;
import static ru.javawebinar.topjava.util.MealsUtil.filterByPredicate;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public MealRestController() {
        service = new MealService();
    }

    public void create(Meal meal) {
        log.info("save(Meal meal) {}", meal);
        checkNew(meal);
        meal.setUserId(authUserId());
        service.create(meal);
    }

    public void update(Meal meal) {
        log.info("update {}", meal.toString());
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete(int id) {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get(int id) {}", id);
        return service.get(id, authUserId());
    }

    public Collection<MealTo> getAll(String startDate, String endDate, String startTime, String endTime) {
        log.info("getAll(startDate, endDate,startTime, endTime) {} {} {} {}", startDate, endDate,startTime, endTime);

        Predicate<Meal> filter = meal -> isBetweenTimeHalfOpen(meal.getTime(),
                startTime == null || startTime.isEmpty() ? MIN : parse(startTime),
                endTime == null || endTime.isEmpty() ? MAX : parse(endTime));

        return filterByPredicate(service.getAll(startDate, endDate, authUserId()), authUserCaloriesPerDay(), filter);
    }
}
