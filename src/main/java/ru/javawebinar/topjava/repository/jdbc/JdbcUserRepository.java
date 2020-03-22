package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    public static class UserResultSetExtractor implements ResultSetExtractor<List<User>> {

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

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            user = addUserRoles(user.getId(), user.getRoles());
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        String sql = "SELECT u.*, ur.role FROM users u JOIN user_roles ur ON u.id = ur.user_id AND u.id=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{id}, new UserResultSetExtractor());
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public User getByEmail(String email) {
        //List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        String sql = "SELECT u.*, ur.role FROM users u JOIN user_roles ur ON u.id = ur.user_id AND u.email=?";
        List<User> users = jdbcTemplate.query(sql, new Object[]{email}, new UserResultSetExtractor());
        return DataAccessUtils.singleResult(users);
    }

    @Override
    public List<User> getAll() {
        String sql = "SELECT u.*, ur.role FROM users u LEFT JOIN user_roles ur ON u.id = ur.user_id";
        return jdbcTemplate.query(sql, new UserResultSetExtractor());
    }

    public User addUserRoles(int id, Set<Role> roles) {
        List<Role> roleList = new ArrayList<>(roles);

        jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) VALUES (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, roleList.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return roleList.size();
            }
        });

        return get(id);
    }


    public User deleteUserRoles(int id, Set<Role> roles) {
        List<Role> roleList = new ArrayList<>(roles);

        jdbcTemplate.batchUpdate("DELETE FROM user_roles ur WHERE ur.user_id=? AND ur.role=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, roleList.get(i).toString());
            }

            @Override
            public int getBatchSize() {
                return roleList.size();
            }
        });

        return getWithRoles(id);
    }

}
