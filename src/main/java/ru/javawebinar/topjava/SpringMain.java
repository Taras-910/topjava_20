package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;

import java.time.LocalDate;
import java.time.LocalTime;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//                 System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //      AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //       adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));
            //      adminUserController.update(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN), 2);
//            MealRestController controller = appCtx.getBean(MealRestController.class);
            MealService mealService = appCtx.getBean(MealService.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            InMemoryMealRepository repo = appCtx.getBean(InMemoryMealRepository.class);

            System.out.println(mealService.getAll(2)+"\n\n");
//            System.out.println(repo.get(4,2)+"\n\n");
//            System.out.println(mealService.get(4,2)+"\n\n");
            System.out.println(mealRestController.getAll(LocalTime.MIN, LocalTime.MAX, LocalDate.MIN,LocalDate.MAX));


        }
    }
}

