package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.InMemoryUserMealRepository;
import ru.javawebinar.topjava.DAO.UserMealRepository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);
    private UserMealRepository repository;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        repository = new InMemoryUserMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String action = req.getParameter("action");
        if (action == null) {
            log.info("GetALL()");
            req.setAttribute("mealList", MealsUtil.getFilteredWithExceeded(repository.getAll(),2000));
            req.getRequestDispatcher("mealList.jsp").forward(req,resp);
        } else if (action.equals("delete")) {
            int id = getId(req);
            repository.delete(id);
            log.info("Delete{}" + id);
            resp.sendRedirect("mealList");
        } else {
            final UserMeal userMeal = action.equals("create") ?
                    new UserMeal(LocalDateTime.now(), "", 1000) :
                    repository.get(getId(req));
            req.setAttribute("meal", userMeal);
            req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
        }
    }

    private int getId(HttpServletRequest req) {
        String paramId = Objects.requireNonNull(req.getParameter("id"));
        return Integer.valueOf(paramId);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String id = req.getParameter("id");
        UserMeal userMeal = new UserMeal(id.isEmpty() ? null : Integer.valueOf(id),
                LocalDateTime.parse(req.getParameter("dateTime")),
                req.getParameter("description"),
                Integer.valueOf(req.getParameter("calories")));
        log.info(userMeal.isNew() ? "Create" : "Update");
        repository.save(userMeal);
        resp.sendRedirect("mealList");
    }
}
