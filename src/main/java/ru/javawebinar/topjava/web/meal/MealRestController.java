package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.To.MealTo;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.function.Predicate;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetween;
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
        service.create(meal, authUserId());
    }

    public void update(Meal meal) {
        log.info("update {}", meal.toString());
        meal.setUserId(authUserId());
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

    public List<MealTo> getAll(LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        log.info("getAll(startDate, endDate,startTime, endTime) {} {} {} {}", startDate, endDate, startTime, endTime);
        Predicate<Meal> filter = meal -> isBetween(meal.getTime(), startTime, endTime);
        return filterByPredicate(service.getAll(startDate, endDate, authUserId()), authUserCaloriesPerDay(), filter);
    }

    public List<MealTo> getAll(){
        return MealsUtil.getTos(service.getAll(authUserId()), authUserCaloriesPerDay(), meal -> true);
    }

}
