package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User createUser(User user) {
        ValidatorUser.validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User deleteUser(User user) {
        ValidatorUser.validate(user);
        return users.remove(user.getId());
    }

    @Override
    public User getUser(int id) {
        ValidatorUserId.validate(users, id);
        return users.get(id);
    }

    @Override
    public User updateUser(User user) {
        ValidatorUser.validate(user);
        ValidatorUserId.validate(users, user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    public Map<Integer, User> getUsersMap() {
        return users;
    }
}
