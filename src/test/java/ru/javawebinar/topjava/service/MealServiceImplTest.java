package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceImplTest {

    @Autowired
    private MealService mealService;

    @Test
    public void getUserMeal() {
        Meal meal1 = mealService.get(MEAL1.getId(), USER_ID);
        assertMatch(meal1, MEAL1);
    }

    @Test
    public void getAdminMeal() {
        Meal meal7 = mealService.get(MEAL7.getId(), ADMIN_ID);
        assertMatch(MEAL7, meal7);
    }

    @Test(expected = NotFoundException.class)
    public void getAlientMeal() {
        mealService.get(ADMIN_ID, MEAL1.getId());
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlientMeal() {
        mealService.delete(MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlientMeal() {
        mealService.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL9.getId(), ADMIN_ID);
        assertMatch(Arrays.asList(MEAL8, MEAL7), mealService.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> allBetween = mealService.getBetweenDateTimes(LocalDateTime.of(2015, 05, 29, 12, 00), LocalDateTime.of(2015, 05, 30, 21, 00), USER_ID);
        assertMatch(Arrays.asList(MEAL1, MEAL2, MEAL3), allBetween);
    }

    @Test
    public void getAllAdminMeal() {
        List<Meal> all = mealService.getAll(ADMIN_ID);
        assertMatch(adminMeal, all);
    }

    @Test
    public void getAllUserMeal() {
        List<Meal> all = mealService.getAll(USER_ID);
        assertMatch(userMeal, all);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        meal.setDescription("breakfast");
        mealService.update(meal, USER_ID);
        assertMatch(mealService.get(MEAL1.getId(), USER_ID), meal);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "breakfast", 500);
        mealService.create(newMeal, USER_ID);
        assertMatch(mealService.get(newMeal.getId(), USER_ID), newMeal);
    }
}