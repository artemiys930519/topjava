package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealWithExceed {

    private final LocalDateTime dateTime;
    private String timeToString;
    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealWithExceed(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.dateTime = LocalDateTime.of(dateTime.toLocalDate(),dateTime.toLocalTime());
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
        this.timeToString = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").format(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getTimeToString() {
        return timeToString;
    }


    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}