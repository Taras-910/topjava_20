package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import ru.javawebinar.topjava.service.UserService;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        Logger log = LoggerFactory.getLogger("");
        // java 7 automatic resource management
//        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));

 /*           ConfigurableApplicationContext springContext =
                    new ClassPathXmlApplicationContext(["spring/spring-db.xml"],  false);

            springContext.getEnvironment().setActiveProfiles(...);
            springContext.refresh(); (edited)
*/

//        System.setProperty("spring.profiles.active", Profiles.getActiveDbProfile());
//        context = new ClassPathXmlApplicationContext(
//                new String[]{"spring/spring-app.xml", "spring/spring-db.xml"},false);
//        environment = context.getEnvironment();
//        environment.setActiveProfiles(Profiles.getProfiles());
//        log.info("--------------------------------------------------------------------");
//        for (final String profileName : environment.getActiveProfiles()) {
//            log.info("active profile - {}", profileName);
//        }
//        context.refresh();

        System.setProperty("spring.profiles.active", Profiles.getActiveDbProfile());
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(
                new String[]{"spring/spring-app.xml","spring/spring-db.xml"}, false);
        ConfigurableEnvironment env = ctx.getEnvironment();
        env.setActiveProfiles(Profiles.getProfiles());
        ctx.refresh();
        log.info("--------------------------------------------------------------------");
        for (final String profileName : env.getActiveProfiles()) {
            log.info("active profile - {}", profileName);
        }

        System.out.println("Bean definition names: " + Arrays.toString(ctx.getBeanDefinitionNames()));


        UserService service = ctx.getBean(UserService.class);
//        DataJpaMealRepository dataJpaMealRepository = ctx.getBean("dataJpaMealRepository", DataJpaMealRepository.class);
//        DataJpaUserRepository dataJpaUserRepository = ctx.getBean("dataJpaUserRepository", DataJpaUserRepository.class);
//
//
//        User user = dataJpaUserRepository.getWithMeals(USER_ID);
//        System.out.println("====================================\n");
//        System.out.println("user = "+user);
//        System.out.println("userMeals = "+user.getMeals());



        ctx.close();
//        for (final String profileName : env.getActiveProfiles()) {
//            System.out.println("====================================\n");
//            System.out.println("Currently active profile - "+ profileName);
//        }


/*
        String[] profiles = Profiles.getProfiles();
        log.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        for(String p : profiles) {
            log.info("profiles - {}", p);
        }
*/

/*
        for(Meal m : MealTestData.MEALS) {
            log.info("meal - {}", m);
        }
*/


    }
}
