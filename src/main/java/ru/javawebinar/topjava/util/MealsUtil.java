package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;

public class MealsUtil {


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = userMeals.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories))
//                      Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum)
                );

        return userMeals.stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> createWithExceed(userMeal, caloriesSumByDate.get(userMeal.getLocalDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static Collection<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> userMeals, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = userMeals.stream()
                .collect(
                        Collectors.groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories))
//                      Collectors.toMap(UserMeal::getDate, UserMeal::getCalories, Integer::sum)
                );

        return userMeals.stream()
                .map(userMeal -> createWithExceed(userMeal, caloriesSumByDate.get(userMeal.getLocalDate()) > caloriesPerDay))
                .collect(toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        userMeals.forEach(userMeal -> caloriesSumByDate.merge(userMeal.getLocalDate(), userMeal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealsWithExceeded = new ArrayList<>();
        userMeals.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(userMeal, caloriesSumByDate.get(userMeal.getLocalDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static List<UserMealWithExceed> getFilteredWithExceededInOnePass(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Collection<List<UserMeal>> list = userMeals.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate)).values();

        return list.stream().flatMap(dayMeals -> {
            boolean exceed = dayMeals.stream().mapToInt(UserMeal::getCalories).sum() > caloriesPerDay;
            return dayMeals.stream().filter(userMeal ->
                    TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                    .map(userMeal -> createWithExceed(userMeal, exceed));
        }).collect(toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededInOnePass2(List<UserMeal> userMeals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        final class Aggregate {
            private final List<UserMeal> dailyMeals = new ArrayList<>();
            private int dailySumOfCalories;

            private void accumulate(UserMeal meal) {
                dailySumOfCalories += meal.getCalories();
                if (TimeUtil.isBetween(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                    dailyMeals.add(meal);
                }
            }

            // never invoked if the upstream is sequential
            private Aggregate combine(Aggregate that) {
                this.dailySumOfCalories += that.dailySumOfCalories;
                this.dailyMeals.addAll(that.dailyMeals);
                return this;
            }

            private Stream<UserMealWithExceed> finisher() {
                final boolean exceed = dailySumOfCalories > caloriesPerDay;
                return dailyMeals.stream().map(userMeal -> createWithExceed(userMeal, exceed));
            }
        }

        Collection<Stream<UserMealWithExceed>> values = userMeals.stream()
                .collect(Collectors.groupingBy(UserMeal::getLocalDate,
                        Collector.of(Aggregate::new, Aggregate::accumulate, Aggregate::combine, Aggregate::finisher))
                ).values();

        return values.stream().flatMap(identity()).collect(toList());
    }

    public static UserMealWithExceed createWithExceed(UserMeal userMeal, boolean exceeded) {
        return new UserMealWithExceed(userMeal.getId(), userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceeded);
    }
}