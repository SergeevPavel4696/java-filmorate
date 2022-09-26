package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

@Slf4j
public class ValidatorUser {
    public static void validate(User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }
        if (user.getEmail() == null || !user.getEmail().contains("@")) {
            log.info("Адрес электронной почты не указан, либо указан некорректно.");
            throw new ValidationException("Адрес электронной почты не указан, либо указан некорректно.");
        }
        if (user.getLogin() == null || user.getLogin().isEmpty() || user.getLogin().contains(" ")) {
            log.info("Логин не указан, либо указан некорректно.");
            throw new ValidationException("Логин не указан, либо указан некорректно.");
        }
        if (user.getBirthday() != null && !user.getBirthday().isBefore(LocalDate.now())) {
            log.info("Дата рождения указана некорректно.");
            throw new ValidationException("Дата рождения указана некорректно.");
        }
    }
}
