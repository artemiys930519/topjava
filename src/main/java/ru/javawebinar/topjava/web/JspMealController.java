package ru.javawebinar.topjava.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Controller
public class JspMealController {

    @Autowired
    private MealService service;

    @GetMapping("/meals")
    public String meals(HttpServletRequest request, Model model) {

        String action = request.getParameter("action");
        int userId = SecurityUtil.authUserId();

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                service.delete(id, userId);
                model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
                return "meals";
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        service.get(getId(request), userId);
                model.addAttribute("meal", meal);
                return "mealForm";
            case "all":
            default:
                model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId),SecurityUtil.authUserCaloriesPerDay()));
                break;
        }
        return "meals";
    }

    @PostMapping("/meals")
    public String setMeal(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        String action = request.getParameter("action");
        int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            service.create(meal, userId);
        } else {
            meal.setId(Integer.valueOf(request.getParameter("id")));
            service.update(meal, userId);
        }
        model.addAttribute("meals", MealsUtil.getWithExcess(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }

    @GetMapping("/mealForm")
    public String mealForm(HttpServletRequest request, @RequestBody Meal meal, Model model) {
        model.addAttribute("meals", service.getAll(SecurityUtil.authUserId()));
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }
}
