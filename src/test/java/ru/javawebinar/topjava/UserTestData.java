package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Collections;
import java.util.Date;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsComparator("registered", "roles", "meals");
    public static TestMatcher<Role> ROLE_MATCHER = TestMatcher.usingFieldsComparator("registered", "roles", "meals");

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int USER_1 = START_SEQ + 2;
    public static final int USER_2 = START_SEQ + 3;
    public static final int USER_3 = START_SEQ + 4;

    public static final Role ROLE_ADMIN = Role.ROLE_ADMIN;
    public static final Role ROLE_USER = Role.ROLE_USER;
    public static final Role ROLE_CONTROLLER = Role.ROLE_CONTROLLER;
    public static final Role ROLE_EDITOR = Role.ROLE_EDITOR;
    public static final Role ROLE_GOD = Role.ROLE_GOD;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        return updated;
    }
}
