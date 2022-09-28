package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

@Component("friendStorage")
public class DBFriendStorage implements FriendStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserStorage userStorage;

    @Override
    public Integer addFriend(int meId, int friendId) {
        String query = "INSERT INTO friendships (user_id, friend_id) VALUES (?, ?);";
        jdbcTemplate.update(query, meId, friendId);
        return userStorage.get(meId).getFriends().size();
    }

    @Override
    public Integer deleteFriend(int meId, int friendId) {
        String query = "DELETE FROM friendships WHERE user_id = ? AND friend_id = ?;";
        jdbcTemplate.update(query, meId, friendId);
        return userStorage.get(meId).getFriends().size();
    }
}
