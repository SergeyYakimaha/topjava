package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import java.util.List;

public interface MealDAO {
    List<MealTo> getAll();
    Meal get(int id);
    Meal create(Meal meal);
    void update(Meal meal);
    void delete(int id);
}
