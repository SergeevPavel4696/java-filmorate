package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        ValidatorUser.validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        ValidatorUser.validate(user);
        return users.remove(user.getId());
    }

    @Override
    public User get(int userId) {
        ValidatorUserId.validate(users, userId);
        return users.get(userId);
    }

    @Override
    public User update(User user) {
        ValidatorUser.validate(user);
        ValidatorUserId.validate(users, user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public ArrayList<User> getAll() {
        return new ArrayList<>(users.values());
    }

    public Map<Integer, User> getUsersMap() {
        return users;
    }
}
