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
    private MealService mealRepository;

    @Test
    public void getUserMeal() {
        Meal meal1 = mealRepository.get(MEAL1.getId(), USER_ID);
        assertMatch(MEAL1, meal1);
    }

    @Test
    public void getAdminMeal() {
        Meal meal7 = mealRepository.get(MEAL7.getId(), ADMIN_ID);
        assertMatch(MEAL7, meal7);
    }

    @Test(expected = NotFoundException.class)
    public void getAlientMeal() {
        mealRepository.get(MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlientMeal() {
        mealRepository.delete(MEAL1.getId(), ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlientMeal() {
        mealRepository.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void delete() {
        mealRepository.delete(MEAL9.getId(), ADMIN_ID);
        assertMatch(Arrays.asList(MEAL8, MEAL7), mealRepository.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> allBetween = mealRepository.getBetweenDateTimes(LocalDateTime.of(2015, 05, 29, 12, 00), LocalDateTime.of(2015, 05, 30, 21, 00), USER_ID);
        assertMatch(Arrays.asList(MEAL1, MEAL2, MEAL3), allBetween);
    }

    @Test
    public void getAllAdminMeal() {
        List<Meal> all = mealRepository.getAll(ADMIN_ID);
        assertMatch(all, adminMeal);
    }

    @Test
    public void getAllUserMeal() {
        List<Meal> all = mealRepository.getAll(USER_ID);
        assertMatch(all, userMeal);
    }

    @Test
    public void update() {
        Meal meal = new Meal(MEAL1.getId(), MEAL1.getDateTime(), MEAL1.getDescription(), MEAL1.getCalories());
        meal.setDescription("breakfast");
        mealRepository.update(meal, USER_ID);
        assertMatch(meal, mealRepository.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "breakfast", 500);
        mealRepository.create(newMeal, USER_ID);
        assertMatch(newMeal, mealRepository.get(newMeal.getId(), USER_ID));
    }
}