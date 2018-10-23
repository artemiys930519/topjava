package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

public class MealTestData {
    private static final Integer USERMEALID = AbstractBaseEntity.START_SEQ + 2;
    private static final Integer ADMINMEALID = AbstractBaseEntity.START_SEQ + 8;

    public static final Meal MEAL1 = new Meal(USERMEALID, LocalDateTime.of(2015, 05, 30, 10, 00), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(USERMEALID + 1, LocalDateTime.of(2015, 05, 30, 10, 00), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(USERMEALID + 2, LocalDateTime.of(2015, 05, 30, 10, 00), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(USERMEALID + 3, LocalDateTime.of(2015, 05, 31, 10, 00), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(USERMEALID + 4, LocalDateTime.of(2015, 05, 31, 10, 00), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(USERMEALID + 5, LocalDateTime.of(2015, 05, 31, 10, 00), "Ужин", 510);
    public static final Meal MEAL7 = new Meal(ADMINMEALID, LocalDateTime.of(2015, 05, 30, 10, 00), "Завтрак", 1000);
    public static final Meal MEAL8 = new Meal(ADMINMEALID + 1, LocalDateTime.of(2015, 05, 30, 10, 00), "Обед", 510);
    public static final Meal MEAL9 = new Meal(ADMINMEALID + 2, LocalDateTime.of(2015, 05, 30, 10, 00), "Ужин", 510);

}
