package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;

public class MealTo {
    private Integer id;
    private final LocalDateTime dateTime;
    private final String description;

    private final int calories;

    private final boolean exceed;

    public MealTo(LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this(null,dateTime,description,calories,exceed);
    }

    public MealTo(Integer id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        this.id =id;
        this.dateTime = LocalDateTime.of(dateTime.toLocalDate(), dateTime.toLocalTime());
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }
    public long getId() {
        return id;
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

    public boolean isExceed() {
        return exceed;
    }

    @Override
    public String toString() {
        return "MealTo{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}