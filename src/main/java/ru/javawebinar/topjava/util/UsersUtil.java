package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.to.UserTo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User("User 1", "1", "user1@gmail.com"),
            new User("User 2", "2", "user2@gmail.com"),
            new User("User 3", "3", "user3@gmail.com")
    );

    public static List<UserTo> getTos(Collection<User> users) {
        List<UserTo> userToList = new ArrayList<>();
        users.forEach(user -> userToList.add(createTo(user)));
        return userToList;
        //return filteredByStreams(meals, caloriesPerDay, meal -> true);
    }

    private static UserTo createTo(User user) {
        return new UserTo(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.isEnabled(), user.getRegistered(), user.getRoles(), user.getCaloriesPerDay());
    }
}
