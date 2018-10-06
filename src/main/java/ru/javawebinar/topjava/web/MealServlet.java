package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.DaoController;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private static String mealsListUrl = "/meals.jsp";
    private static String mealUrl = "/meal.jsp";

    public MealServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //log.debug("redirect to meals");
        String forward = "";
        String action = req.getParameter("action");
        if (action == null) {
            forward = mealsListUrl;
            req.setAttribute("meals", DaoController.getUserMealWithExceed());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = mealUrl;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            MealWithExceed meal = DaoController.getMealWithExceed(mealId);
            req.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            DaoController.deleteMealWithExceed(mealId);
            forward = mealsListUrl;
            req.setAttribute("meals", DaoController.getUserMealWithExceed());
        }else if(action.equalsIgnoreCase("insert")) {
            forward = mealUrl;
        }

        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        long mealId = Long.parseLong(req.getParameter("mealId"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime mealDateTime = LocalDateTime.parse(req.getParameter("dateTime"),formatter);

        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("mealId");
        Meal meal = new Meal(mealId,mealDateTime,description,calories);

        if(id != null || !id.isEmpty()) {
            DaoController.addMeal(meal);
        }
        req.setAttribute("meals",DaoController.getUserMealWithExceed());
        req.getRequestDispatcher(mealsListUrl).forward(req,resp);
    }
}
