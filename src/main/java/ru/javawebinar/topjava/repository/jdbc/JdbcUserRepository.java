package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@Repository
public class JdbcUserRepository implements UserRepository {
    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    @Override
    public User save(@NotNull User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);
        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
        } else if (namedParameterJdbcTemplate.update("UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        } else {
            if (!deleteRoles(user)){
                return null;
            }
        }
        saveRoles(user);
        return user;
    }

    @Transactional
    @Override
    public boolean delete(@Positive int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(@Positive int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(users);
    }

    @Override
    public User getByEmail(@Email String email) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(users);
    }

    @Override
    public List<User> getAll() {
        Map<Integer,List<Role>> map = new HashMap<>();
        jdbcTemplate.query("SELECT * FROM user_roles", new ResultSetExtractor<Map<Integer,List<Role>>>(){
            public Map<Integer,List<Role>> extractData(ResultSet rs) throws SQLException, DataAccessException {
                while(rs.next()){
                    List<Role> roles = new ArrayList<>();
                    int key = rs.getInt("user_id");
                    roles.add(Role.valueOf(rs.getString("role")));
                    map.merge(key, roles, (oldVal, newVal) -> {
                        oldVal.addAll(newVal);
                        return oldVal;
                    });
                }
                return map;
            }
        });
        List<User> users =  jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        users.forEach(u -> u.setRoles(map.get(u.getId())));
        System.out.println("===================================================");
        System.out.println(users);
        return users;
    }

    private void saveRoles(User user) {
        List<Role> roles = new ArrayList<>(user.getRoles());
        jdbcTemplate.batchUpdate(
                "INSERT INTO user_roles (user_id, role) VALUES (?,?)",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setInt(1, user.getId());
                        ps.setString(2, roles.get(i).name());
                    }
                    @Override
                    public int getBatchSize() {
                        return roles.size();
                    }
                });
    }

    private boolean deleteRoles(User user) {
        return jdbcTemplate.update("DELETE FROM user_roles WHERE user_id=?", user.getId()) != 0;
    }

    private User setRoles(List<User> users){
        User user = DataAccessUtils.singleResult(users);
        if(user != null) {
            user.setRoles(jdbcTemplate.queryForList("SELECT role FROM user_roles  WHERE user_id=?", Role.class, user.getId()));
        }
        return user;
    }
}
