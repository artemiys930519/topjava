package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExceed;

public class DaoController {
    private static List<MealWithExceed> mealsWithExceed;
    private static int caloriesPerDay = 2000;
    private static ArrayList<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
    ));

    public static List<MealWithExceed> getUserMealWithExceed() {
        Collection<List<Meal>> list = meals.stream()
                .collect(Collectors.groupingBy(Meal::getDate)).values();

        mealsWithExceed = list.stream().flatMap(dayMeals -> {
            boolean exceed = dayMeals.stream().mapToInt(Meal::getCalories).sum() > caloriesPerDay;
            return dayMeals.stream()
                    .map(meal -> createWithExceed(meal, exceed));
        }).collect(toList());
        return mealsWithExceed;
    }

    public static MealWithExceed getMealWithExceed(long id) {
        MealWithExceed result = null;
        for(MealWithExceed meal: mealsWithExceed) {
            if(meal.getId() == id) {
                result = meal;
            }
        }
        return result;
    }

    public static void deleteMealWithExceed(long id) {
        Meal result = null;
        for(Meal meal: meals) {
            if(meal.getId() == id) {
                result = meal;
            }
        }
        meals.remove(result);
    }

    public static void addMeal(Meal meal) {
        boolean mealBool= false;
        for(Meal m: meals) {
            if(m.getId()== meal.getId()) {
                m.setCalories(meal.getCalories());
                m.setDateTime(meal.getDateTime());
                m.setDescription(meal.getDescription());
                mealBool = true;
                System.out.println(mealBool);
            }
        }
        System.out.println("mealBool" + mealBool);
        if(!mealBool) {
            meals.add(meal);
        }
    }
}
