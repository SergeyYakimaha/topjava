package ru.javawebinar.topjava.model;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

    @Override
    public List<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<Integer, User> userById = new HashMap<>();

        while (rs.next()) {
            int id = rs.getInt("id");
            userById.putIfAbsent(id, new User());
            User user = userById.get(id);
            if (user.getId() == null) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRegistered(rs.getDate("registered"));
                user.setEnabled(rs.getBoolean("enabled"));
                user.setCaloriesPerDay(rs.getInt("calories_per_day"));
                user.setRoles(null);
            }
            Role role = Role.valueOf(rs.getString("role"));
            Role[] roles = user.getRoles().toArray(new Role[0]);
            user.setRoles(EnumSet.of(role, roles));
        }

        return new ArrayList<>(userById.values());
    }

}
