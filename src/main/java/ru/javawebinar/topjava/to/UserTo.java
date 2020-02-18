package ru.javawebinar.topjava.to;

import ru.javawebinar.topjava.model.Role;

import java.util.Date;
import java.util.Set;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class UserTo {
    private final Integer id;

    private final String name;

    private final String email;

    private final String password;

    private final boolean enabled;

    private final Date registered;

    private final Set<Role> roles;

    private final int caloriesPerDay;

    public UserTo(Integer id, String name, String email, String password, boolean enabled, Date registered, Set<Role> roles, int caloriesPerDay) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enabled = enabled;
        this.registered = registered;
        this.roles = roles;
        this.caloriesPerDay = caloriesPerDay;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Date getRegistered() {
        return registered;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public int getCaloriesPerDay() {
        return caloriesPerDay;
    }
}
