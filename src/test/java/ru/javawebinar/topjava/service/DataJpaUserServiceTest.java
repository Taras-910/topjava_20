package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Autowired
    private UserService service;

    @Test
    public void getWithMeals() throws Exception {
        User actual = service.getWithMeals(USER_ID);
        System.out.println("user = "+actual);
        USER.setMeals(MEALS);
        USER_MATCHER.assertMatch(actual, USER);
    }
}
