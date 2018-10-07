package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealWithExceedCrud;
import ru.javawebinar.topjava.DAO.MealWithExceedCrudImpl;
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
    private DateTimeFormatter formatter;
    private MealWithExceedCrud mealWithExceedCrud;
    private boolean delete;
    public MealServlet() {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        mealWithExceedCrud = new MealWithExceedCrudImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        if (action == null) {
            log.debug("show full MealWithExceedList");
            forward = mealsListUrl;
            req.setAttribute("meals", mealWithExceedCrud.getMealWithExceed());
        } else if (action.equalsIgnoreCase("edit")) {
            forward = mealUrl;
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            MealWithExceed meal = mealWithExceedCrud.getMealWithExceed(mealId);
            req.setAttribute("meal", meal);
            log.debug("edit meal operation");
        } else if (action.equalsIgnoreCase("delete")) {
            int mealId = Integer.parseInt(req.getParameter("mealId"));
            mealWithExceedCrud.deleteMeal(mealId);
            log.debug("delete meal operation");
            forward = mealsListUrl;
            req.setAttribute("meals", mealWithExceedCrud.getMealWithExceed());
        } else if (action.equalsIgnoreCase("insert")) {
            forward = mealUrl;
            log.debug("insert meal operation");
        }
        req.getRequestDispatcher(forward).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        LocalDateTime mealDateTime = LocalDateTime.parse(req.getParameter("dateTime"), formatter);

        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("mealId");

        if (!id.equals("")) {
            mealWithExceedCrud.updateMeal(Long.parseLong(id), mealDateTime, description, calories);
        } else {
            Meal meal = new Meal(mealDateTime, description, calories);
            mealWithExceedCrud.addMeal(meal);
        }
        req.setAttribute("meals", mealWithExceedCrud.getMealWithExceed());
        req.getRequestDispatcher(mealsListUrl).forward(req, resp);
    }
}
