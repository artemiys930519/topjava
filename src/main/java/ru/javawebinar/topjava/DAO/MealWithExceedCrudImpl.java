package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealWithExceedCrudImpl implements MealWithExceedCrud {
    private static List<MealWithExceed> mealsWithExceed;
    private static int caloriesPerDay = 2000;
    private static ArrayList<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    public List<MealWithExceed> getMealWithExceed() {
        mealsWithExceed = MealsUtil.getFilteredWithExceeded(meals, caloriesPerDay);
        return mealsWithExceed;
    }

    public MealWithExceed getMealWithExceed(long id) {
        MealWithExceed result = null;
        for (MealWithExceed meal : mealsWithExceed) {
            if (meal.getId() == id) {
                result = meal;
            }
        }
        return result;
    }

    public void deleteMeal(long id) {
        Meal result = null;
        for (Meal meal : meals) {
            if (meal.getId() == id) {
                result = meal;
            }
        }
        meals.remove(result);
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public void updateMeal(long id, LocalDateTime localDateTime, String description, int calories) {
        for (Meal m : meals) {
            if (m.getId() == id) {
                m.setCalories(calories);
                m.setDateTime(localDateTime);
                m.setDescription(description);
            }
        }
    }
}
