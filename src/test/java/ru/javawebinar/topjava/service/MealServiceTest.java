package ru.javawebinar.topjava.service;

import javafx.scene.control.Tooltip;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_AUTH_USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void getAuthUser() {
        Meal meal = service.get(MEAL_ID_100005, SecurityUtil.authUserId());
        assertMatch(meal, MEAL_100005);
    }

    @Test(expected = NotFoundException.class)
    public void getNotAuthUser() {
        Meal meal = service.get(MEAL_ID_100005, NOT_AUTH_USER_ID);
        assertMatch(meal, MEAL_100005);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAuthUser() throws Exception {
        service.delete(MEAL_ID_100005, SecurityUtil.authUserId());
        service.get(MEAL_ID_100005, SecurityUtil.authUserId());
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotAuthUser() throws Exception {
        service.delete(MEAL_ID_100005, NOT_AUTH_USER_ID);
        service.get(MEAL_ID_100005, NOT_AUTH_USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
    }

    @Test
    public void getAll() {
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, SecurityUtil.authUserId());
        assertMatch(service.get(MEAL_ID_100005, SecurityUtil.authUserId()), updated);
    }

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = service.create(newMeal, SecurityUtil.authUserId());
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, SecurityUtil.authUserId()), newMeal);
    }
}