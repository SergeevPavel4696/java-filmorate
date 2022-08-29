package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmRequestException;
import ru.yandex.practicum.filmorate.exceptions.UserRequestException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    private void validateUser(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        String incorrect = "";
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            incorrect = incorrect + "Адрес электронной почты не указан, либо указан некорректно.\n";
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            incorrect = incorrect + "Логин не указан, либо указан некорректно.\n";
        }
        if (user.getBirthday() != null && !user.getBirthday().isBefore(LocalDate.now())) {
            incorrect = incorrect + "Дата рождения указана некорректно.\n";
        }
        if (!incorrect.isEmpty()) {
            log.info(incorrect);
            throw new UserRequestException(incorrect);
        }
    }

    private void validateId(Integer id) {
        if (id == null) {
            log.info("Вы передали пустой id");
            throw new FilmRequestException("Вы передали пустой id");
        }
        if (!users.containsKey(id)) {
            log.info("Пользователь по указанному id не существует.\n");
            throw new FilmRequestException("Пользователь по указанному id не существует.\n");
        }
    }

    @Override
    public User createUser(User user) {
        validateUser(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User deleteUser(User user) {
        validateId(user.getId());
        return users.remove(user.getId());
    }

    @Override
    public User getUser(int id) {
        validateId(id);
        return users.get(id);
    }

    @Override
    public User updateUser(User user) {
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            log.info("Такого пользователя нет.");
            throw new FilmRequestException("Такого пользователя нет.");
        }
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
