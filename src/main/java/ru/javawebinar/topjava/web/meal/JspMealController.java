package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static java.util.Objects.requireNonNull;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
public class JspMealController extends AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @PostMapping("/meals/**")
    public String save(HttpServletRequest request) {
        log.info("Post save");
        Meal meal = new Meal(
                LocalDateTime.parse(requireNonNull(request.getParameter("dateTime"))),
                request.getParameter("description"),
                Integer.parseInt(requireNonNull(request.getParameter("calories"))));
        if (StringUtils.isEmpty(request.getParameter("id"))) {
            super.create(meal);
        } else {
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            super.update(meal, id);
        }
        return "redirect:/meals";
    }

    @GetMapping("/meals/create")
    public String create(Model model) {
        log.info("createMeal");
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals/update/{mealId}")
    public String update(@PathVariable(value = "mealId") Integer mealId, Model model) {
        log.info("update");
        Meal meal =  super.get(mealId);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/meals")
    public String get(Model model) {
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/meals/delete/{mealId}")
    public String delete(@PathVariable("mealId") Integer mealId) {
        log.info("delete for mealId {}", mealId);
        super.delete(mealId);
        return "redirect:/meals";
    }

    @GetMapping("/meals/filter")
    public String filter(HttpServletRequest request, Model model) {
        log.info("filter");
        model.addAttribute("meals", super.getBetween(
                parseLocalDate(request.getParameter("startDate")), parseLocalTime(request.getParameter("startTime")),
                parseLocalDate(request.getParameter("endDate")), parseLocalTime(request.getParameter("endTime"))));
        return "meals";
    }

 @GetMapping("/meals/*")
    public String getDefault(Model model) {
        log.info("getMealsDefault");
        model.addAttribute("meals", super.getAll());
        return "forward:/meals";
    }
}
