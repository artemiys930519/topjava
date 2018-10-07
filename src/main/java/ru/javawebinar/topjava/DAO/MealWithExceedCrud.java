package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface MealWithExceedCrud {
    Collection<MealWithExceed> getMealWithExceed();
    MealWithExceed getMealWithExceed(long id);
    void deleteMeal(long id);
    void addMeal(Meal meal);
    void updateMeal(long id, LocalDateTime localDateTime, String description, int calories);
}
