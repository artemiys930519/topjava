package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithMeals(ADMIN_ID);
        assertMatch(ADMIN, user);
        MealTestData.assertMatch(List.of(ADMIN_MEAL1, ADMIN_MEAL2), user.getMeals());
    }
    @Test
    public void getWithMealsNotFoundUser() {
        thrown.expect(NotFoundException.class);
        service.getWithMeals(0);
    }
    @Test
    public void getWithMealsNotFoundMeals() {
        thrown.expect(NotFoundException.class);
        User user = service.getWithMeals(ADMIN_ID);
    }
}
