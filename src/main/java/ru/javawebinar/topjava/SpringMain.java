package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDateTime;
import java.time.Month;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            MealService mealService = appCtx.getBean(MealService.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            InMemoryMealRepository repository = appCtx.getBean(InMemoryMealRepository.class);

            int id = 1;
            int userId = 1;
            SecurityUtil.setAuthId(userId);
            Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 555);
            meal.setId(id);

            Meal meal1 = mealService.get(id, userId);
            System.out.println("\nrepository.get("+id+") = "+meal1+"\n");
/*

            mealRestController.delete(3);
            System.out.println("\n");

            System.out.println("mealService.getAll ="+ mealService.getAll(userId)+"\n");
*/
        }
    }
}

