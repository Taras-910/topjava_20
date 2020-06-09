package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    private MealService mealService;

    @Override
    public void init() throws ServletException {
        mealService = new MealService();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals doGet");
/*
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
//        response.sendRedirect("meals.jsp");
*/

        String action = request.getParameter("action");
        log.debug("action {}",action);

        if (action == null || action.equals("") || action.equalsIgnoreCase("listMeal")) {
            List<MealTo> list = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", list);
            request.setAttribute("formatter", TimeUtil.formatter);
            RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
            view.forward(request, response);

        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealService.delete(mealId);
            List<MealTo> list = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", list);
            request.setAttribute("formatter", TimeUtil.formatter);
            RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
            view.forward(request, response);

        } else if (action.equalsIgnoreCase("edit")) {
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            log.debug("mealId {}",mealId);

            Meal meal = mealService.getMealById(mealId);
            log.debug("meal {}",meal.toString());

            request.setAttribute("meal", meal);
//        request.getRequestDispatcher("/meals.jsp"). forward(request, response);
            RequestDispatcher view = request.getRequestDispatcher("mealsForm.jsp");
            view.forward(request, response);

        } else if (action.equalsIgnoreCase("create")) {
            request.setAttribute("meal", new Meal());
//        request.getRequestDispatcher("/meals.jsp"). forward(request, response);
            RequestDispatcher view = request.getRequestDispatcher("mealsForm.jsp");
            view.forward(request, response);
        }
    }


    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals doPost");
        String mealId = request.getParameter("mealId");
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        Meal meal = new Meal(dateTime, description, calories);

        if(mealId == null || mealId.isEmpty()){
            mealService.add(meal);
        }
        else {
            meal.setId(Integer.parseInt(mealId));
            mealService.update(meal, Integer.parseInt(mealId));
        }

        List<MealTo> list = MealsUtil.filteredByStreams(mealService.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", list);
        request.setAttribute("formatter", TimeUtil.formatter);
        RequestDispatcher view = request.getRequestDispatcher("meals.jsp");
        view.forward(request, response);
    }
}
