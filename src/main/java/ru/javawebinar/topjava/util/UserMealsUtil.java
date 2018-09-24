package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import javax.management.ObjectName;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        getFilteredWithExceededStream(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000).forEach(System.out::println);
        //.toLocalTime();
    }

    private static List<UserMealWithExceed> getFilteredWithExceededStream(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> meals =  mealList.stream()
                .collect(Collectors.groupingBy(UserMeal::getDate,Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(u ->
                TimeUtil.isBetween(LocalTime.of(u.getDateTime().getHour(),u.getDateTime().getMinute()),startTime,endTime))
                .map((m) -> new UserMealWithExceed(m, meals.get(m.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> meals = new HashMap<>();
        List<UserMealWithExceed> result = new ArrayList<>();

        for(UserMeal meal : mealList) {
            meals.merge(meal.getDateTime().toLocalDate(),meal.getCalories(),(oldVal,newVal) -> oldVal+newVal );
        }

        for(UserMeal meal : mealList) {
            LocalTime mealTime = LocalTime.of(meal.getDateTime().getHour(),meal.getDateTime().getMinute());

            if(TimeUtil.isBetween(mealTime, startTime, endTime)){
                result.add(new UserMealWithExceed(meal, meals.get(meal.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }

        return result;
    }

}
