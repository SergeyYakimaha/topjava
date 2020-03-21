package ru.javawebinar.topjava.service.tests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.ActiveDbProfileResolver;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.util.ValidationUtil.getRootCause;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
//@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@ActiveProfiles(profiles = {"postgres"})
public abstract class MainTest {

    @Test
    public void print() {
        System.out.println(this.getClass().getSimpleName());
    }
}
