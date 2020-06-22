package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 10;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL_ID1 = START_SEQ + 2;
    public static final int MEAL_ID2 = START_SEQ + 3;
    public static final int MEAL_ID3 = START_SEQ + 4;
    public static final int USER_ID = START_SEQ;

    public static Meal MEAL_2 = new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static Meal MEAL_3 = new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static Meal MEAL_4 = new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static Meal MEAL_5 = new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static Meal MEAL_6 = new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static Meal MEAL_7 = new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static Meal MEAL_8 = new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410);

    public static Meal[] MEALS = new Meal []{MEAL_4, MEAL_3, MEAL_2};

    public static Meal getNew() {
        return new Meal(LocalDateTime.now(), "newDish", 555);
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "dateTime");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("dateTime").isEqualTo(expected);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL_3);
        updated.setId(MEAL_ID2);
        updated.setDateTime(LocalDateTime.of(2010, 10,10,10,10));
        updated.setDescription("updatedDescription");
        updated.setCalories(1010);
        return updated;
    }

    public static List<Meal> getBetween() {
        List<Meal> betweenInclusive = new ArrayList<Meal>();
        betweenInclusive.add(MEAL_4);
        betweenInclusive.add(MEAL_3);
        betweenInclusive.add(MEAL_2);
        return betweenInclusive;
    }
}
