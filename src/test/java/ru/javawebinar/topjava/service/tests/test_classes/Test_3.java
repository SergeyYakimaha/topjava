package ru.javawebinar.topjava.service.tests.test_classes;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.tests.MainTest;

import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.JPA;

@ActiveProfiles(DATAJPA)
public class Test_3 extends MainTest {
    @Autowired
    private MealService mealService;

    @Autowired
    private MealRepository mealRepository;

    @Test
    public void print() {
        System.out.println(this.getClass().getSimpleName());
        System.out.println(this.mealService.getClass().getSimpleName());
        System.out.println(this.mealRepository.getClass().getSimpleName());
    }
}
