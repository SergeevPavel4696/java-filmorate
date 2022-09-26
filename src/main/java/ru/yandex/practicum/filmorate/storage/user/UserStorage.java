package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserStorage {

    User create(User user);

    User delete(User user);

    User get(int userId);

    User update(User user);

    ArrayList<User> getAll();

    Map<Integer, User> getUsersMap();
}
