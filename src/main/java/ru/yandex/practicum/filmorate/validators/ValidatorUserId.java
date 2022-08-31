package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Map;

@Slf4j
public class ValidatorUserId {
    public static void validate(Map<Integer, User> users, Integer userId) {
        if (!users.containsKey(userId)) {
            log.info("Пользователь по указанному id не существует.");
            throw new IncorrectId("Пользователь по указанному id не существует.");
        }
    }

    public static void validate(Map<Integer, User> users, User user) {
        if (!users.containsKey(user.getId())) {
            log.info("Такого пользователя нет.");
            throw new IncorrectId("Такого пользователя нет.");
        }
    }

    public static void validate(Integer user1Id, Integer user2Id) {
        if (user1Id.equals(user2Id)) {
            log.info("Вы не можете дружить с собой.");
            throw new IncorrectId("Вы не можете дружить с собой.");
        }
    }
}
