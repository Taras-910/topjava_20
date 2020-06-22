package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private MealRepository repository;

    @Test
    public void create() {
        Meal newMeal = MealTestData.getNew();
        Meal created = service.create(newMeal, authUserId());
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, authUserId()), newMeal);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_ID, authUserId());
        MEAL_1.setId(meal.getId());
        assertMatch(meal, MEAL_1);
    }

    @Test
    public void getNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, authUserId()));
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, authUserId());
        assertNull(repository.get(MEAL_ID, authUserId()));
    }

    @Test
    public void deletedNotFound() throws Exception {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, authUserId()));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> between = MealTestData.getBetween();
        List<Meal> filtered = service.getBetweenInclusive(
                LocalDate.of(2020,1,28),
                LocalDate.of(2020,1,30), authUserId());
        assertMatch(filtered, between);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(authUserId());
        assertMatch(all, MEALS);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, authUserId());
        assertMatch(service.get(MEAL_ID, authUserId()), updated);
    }

    @Test
    public void updatedNotFound() throws Exception {
        assertThrows(DataIntegrityViolationException.class, () -> service.update(MEAL_2, NOT_FOUND));
    }
}
