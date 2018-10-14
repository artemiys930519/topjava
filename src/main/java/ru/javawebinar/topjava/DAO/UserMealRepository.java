package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;

public interface UserMealRepository {

    UserMeal save(UserMeal userMeal);

    void delete(int id);

    UserMeal get(int id);

    Collection<UserMeal> getAll();

}
