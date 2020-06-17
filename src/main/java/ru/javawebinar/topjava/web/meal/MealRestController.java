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

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
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
        log.info("save meal {}", meal);
        checkNew(meal);
        service.create(meal, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update meal {} id {}", meal,id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete id {}", id);
        service.delete(id, authUserId());
    }

    public Meal get(int id) {
        log.info("get id {}", id);
        return service.get(id, authUserId());
    }

    public List<MealTo> getAllFilteredByDate (LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate) {
        log.info("getAll(startDate, endDate,startTime, endTime) {} {} {} {}", startDate, endDate, startTime, endTime);
        return MealsUtil.getFilteredTos(service.getAllFilteredByDate(
                startDate == null ? LocalDate.of(1,1,1) : startDate,
                endDate == null ? LocalDate.of(2100,1,1) : endDate,
                authUserId()), authUserCaloriesPerDay(),
                startTime == null ? LocalTime.of(0,0,0) : startTime,
                endTime == null ? LocalTime.of(23,59,59) : endTime);
    }
}
