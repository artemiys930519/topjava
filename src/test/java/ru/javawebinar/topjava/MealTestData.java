package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    private static final Integer USERMEALID = AbstractBaseEntity.START_SEQ + 2;
    private static final Integer ADMINMEALID = AbstractBaseEntity.START_SEQ + 8;

    public static final Meal MEAL1 = new Meal(USERMEALID, LocalDateTime.of(2015, 05, 30, 10, 00), "Завтрак", 500);
    public static final Meal MEAL2 = new Meal(USERMEALID + 1, LocalDateTime.of(2015, 5, 30, 13, 00), "Обед", 1000);
    public static final Meal MEAL3 = new Meal(USERMEALID + 2, LocalDateTime.of(2015, 5, 30, 20, 00), "Ужин", 500);
    public static final Meal MEAL4 = new Meal(USERMEALID + 3, LocalDateTime.of(2015, 5, 31, 10, 00), "Завтрак", 500);
    public static final Meal MEAL5 = new Meal(USERMEALID + 4, LocalDateTime.of(2015, 5, 31, 13, 00), "Обед", 1000);
    public static final Meal MEAL6 = new Meal(USERMEALID + 5, LocalDateTime.of(2015, 5, 31, 20, 00), "Ужин", 510);
    public static final Meal MEAL7 = new Meal(ADMINMEALID, LocalDateTime.of(2015, 5, 30, 10, 00), "Завтрак", 1000);
    public static final Meal MEAL8 = new Meal(ADMINMEALID + 1, LocalDateTime.of(2015, 5, 30, 13, 00), "Обед", 510);
    public static final Meal MEAL9 = new Meal(ADMINMEALID + 2, LocalDateTime.of(2015, 5, 30, 20, 00), "Ужин", 510);

    public static List<Meal> userMeal = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    public static List<Meal> adminMeal = Arrays.asList(MEAL9, MEAL8, MEAL7);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user").isEqualTo(expected);
    }
}
