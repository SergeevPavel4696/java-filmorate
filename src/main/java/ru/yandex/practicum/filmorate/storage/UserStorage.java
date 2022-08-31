package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserStorage {

    User create(User user);

    User delete(User user);

    User get(int id);

    User update(User user);

    ArrayList<User> getAllUsers();

    Map<Integer, User> getUsersMap();

}
