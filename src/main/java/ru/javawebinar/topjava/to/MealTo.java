package ru.javawebinar.topjava.to;

import java.time.LocalDateTime;
import java.util.Objects;

public class MealTo extends BaseTo {
    private final LocalDateTime dateTime;
    private String description;

    private int calories;

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
    public Integer getId() {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MealTo that = (MealTo) o;
        return calories == that.calories &&
                exceed == that.exceed &&
                Objects.equals(id, that.id) &&
                Objects.equals(dateTime, that.dateTime) &&
                Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, description, calories, exceed);
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