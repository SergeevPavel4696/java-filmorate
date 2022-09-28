package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserStorage {

    User create(User user);

    User delete(User user);

    User get(int userId);

    User update(User user);

    List<User> getAll();

    Map<Integer, User> getUsersMap();

    Set<Integer> getUserFriends(int userId);
}
