package ru.javawebinar.topjava.model;

import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setRegistered(rs.getDate("registered"));
        user.setEnabled(rs.getBoolean("enabled"));
        user.setCaloriesPerDay(rs.getInt("calories_per_day"));
        return user;
    }
}
