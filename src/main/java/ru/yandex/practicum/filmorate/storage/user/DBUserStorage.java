package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component("userStorage")
public class DBUserStorage implements UserStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User create(User user) {
        ValidatorUser.validate(user);
        String query = "INSERT INTO users (email, login, name, birthday) VALUES (?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(query, new String[]{"id"});
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getLogin());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getBirthday()));
            return statement;
        }, keyHolder);
        user.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return user;
    }

    @Override
    public User delete(User user) {
        ValidatorUser.validate(user);
        String query = "DELETE users WHERE id = ?;";
        jdbcTemplate.update(query, user.getId());
        return user;
    }

    @Override
    public User get(int userId) {
        ValidatorUserId.validate(getUsersMap(), userId);
        String query = "SELECT * FROM users WHERE id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeUser(rs), userId)
                .stream()
                .findAny()
                .orElseThrow(() -> new IncorrectId("Пользователь по указанному id не существует."));
    }

    @Override
    public User update(User user) {
        ValidatorUser.validate(user);
        ValidatorUserId.validate(getUsersMap(), user);
        String query = "UPDATE users SET email = ?, login = ?, name = ?, birthday = ? WHERE id = ?;";
        jdbcTemplate.update(query, user.getEmail(), user.getLogin(), user.getName(), user.getBirthday(), user.getId());
        return user;
    }

    @Override
    public List<User> getAll() {
        String query = "SELECT * FROM users;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeUser(rs));
    }

    @Override
    public Map<Integer, User> getUsersMap() {
        List<User> usersList = getAll();
        Map<Integer, User> usersMap = new HashMap<>();
        for (User user : usersList) {
            usersMap.put(user.getId(), user);
        }
        return usersMap;
    }

    @Override
    public Set<Integer> getUserFriends(int userId) {
        String query = "SELECT friend_id FROM friendships WHERE user_id = ?;";
        return new HashSet<>(jdbcTemplate.queryForList(query, Integer.class, userId));
    }

    @Override
    public Integer addFriend(int meId, int friendId) {
        String query = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?);";
        jdbcTemplate.update(query, meId, friendId);
        return get(meId).getFriends().size();
    }

    @Override
    public Integer deleteFriend(int meId, int friendId) {
        String query = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(query, meId, friendId);
        return get(meId).getFriends().size();
    }

    private User makeUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String login = rs.getString("login");
        String name = rs.getString("name");
        LocalDate birthday = rs.getDate("birthday").toLocalDate();
        Set<Integer> friends = new HashSet<>(getUserFriends(id));
        return new User(id, email, login, name, birthday, friends);
    }
}
