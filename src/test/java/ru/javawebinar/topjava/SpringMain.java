package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.UserService;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml", "spring/spring-db.xml");
            appCtx.refresh();

/*
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
            System.out.println(mealController.getBetween(null, null, null, null));
*/
 //           AdminRestController adminRestController = appCtx.getBean(AdminRestController.class);
            UserService userService = appCtx.getBean(UserService.class);
            System.out.println(userService.get(USER_ID));
            MealService mealService = appCtx.getBean(MealService.class);
            System.out.println(mealService.get(MEAL1_ID, USER_ID));
        }
    }
}
