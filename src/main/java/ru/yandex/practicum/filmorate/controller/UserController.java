package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.FilmRequestException;
import ru.yandex.practicum.filmorate.exceptions.UserRequestException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> users = new HashMap<>();

    private int id = 0;

    private void isUser(User user) {
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

    @PostMapping
    public User createUser(@RequestBody User user) {
        isUser(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        isUser(user);
        if (!users.containsKey(user.getId())) {
            log.info("Такого пользователя нет.");
            throw new FilmRequestException("Такого пользователя нет.");
        }
        users.put(user.getId(), user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
