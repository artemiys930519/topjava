package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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

    private List<Meal> userMeal = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    private List<Meal> adminMeal = Arrays.asList(MEAL9, MEAL8, MEAL7);

    @Autowired
    private MealRepository mealRepository;

    @Test
    public void getUserMeal() {
        Meal meal1 = mealRepository.get(MEAL1.getId(), USER_ID);
        assertThat(MEAL1).isEqualTo(meal1);
    }

    @Test
    public void getAdminMeal() {
        Meal meal1 = mealRepository.get(MEAL7.getId(), ADMIN_ID);
        assertThat(MEAL7).isEqualTo(meal1);
    }

    @Test(expected = NotFoundException.class)
    public void getAlientMeal() {
        checkNotFoundWithId(mealRepository.get(MEAL1.getId(),ADMIN_ID),USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlientMeal() {
        checkNotFoundWithId(mealRepository.delete(MEAL1.getId(),ADMIN_ID),USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlientMeal() {
        checkNotFoundWithId(mealRepository.save(MEAL1,ADMIN_ID),USER_ID);
    }

    @Test
    public void delete() {
        mealRepository.delete(MEAL9.getId(), ADMIN_ID);
        assertThat(Arrays.asList(MEAL8, MEAL7)).isEqualTo(mealRepository.getAll(ADMIN_ID));
    }

    @Test
    public void getBetweenDateTimes() {
        List<Meal> allBetween = mealRepository.getBetween(LocalDateTime.of(2015, 05, 29, 12, 00), LocalDateTime.of(2015, 05, 30, 21, 00), USER_ID);
        assertThat(Arrays.asList(MEAL1, MEAL2, MEAL3)).isEqualTo(allBetween);
    }

    @Test
    public void getAllAdminMeal() {
        List<Meal> all = mealRepository.getAll(ADMIN_ID);
        assertThat(all).isEqualTo(adminMeal);
    }

    @Test
    public void getAllUserMeal() {
        List<Meal> all = mealRepository.getAll(USER_ID);
        assertThat(all).isEqualTo(userMeal);
    }

    @Test
    public void update() {
        Meal meal = MEAL1;
        meal.setDescription("breakfast");
        mealRepository.save(meal, USER_ID);
        assertThat(meal).isEqualTo(mealRepository.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "breakfast", 500);
        mealRepository.save(newMeal, USER_ID);
        assertThat(newMeal).isEqualTo(mealRepository.get(newMeal.getId(), USER_ID));
    }
}