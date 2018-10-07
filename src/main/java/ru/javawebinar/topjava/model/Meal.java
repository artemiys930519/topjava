package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal {
    private long id;

    private static long count = 1;
    private LocalDateTime dateTime;
    private String description;
    private int calories;


    public Meal(LocalDateTime dateTime, String description, int calories) {
        this.id =count;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        count++;
    }

    public long getId() {
        return id;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
