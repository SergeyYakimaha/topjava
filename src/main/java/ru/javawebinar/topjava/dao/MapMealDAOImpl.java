package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toCollection;

public class MapMealDAOImpl implements MealDAO {

    private static Map<Integer, Meal> mealMap = null;
    private static volatile int id = 0;

    private synchronized int getNextId() {
        return ++id;
    }

    public MapMealDAOImpl() {
        if (mealMap == null) {
            mealMap = new ConcurrentHashMap<>();
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
            mealMap.put(getNextId(), new Meal(id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
        }
    }

    @Override
    public List<MealTo> getAll() {
        return MealsUtil.filteredByCycles(new ArrayList<>(mealMap.values()), LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    public Meal create(Meal meal) {
        int id = getNextId();
        meal.setId(id);
        mealMap.put(id, meal);
        return meal;
    }

    @Override
    public void update(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id) {
        mealMap.remove(id);
    }

    @Override
    public Meal get(int id) {
        return mealMap.get(id);
    }
}
