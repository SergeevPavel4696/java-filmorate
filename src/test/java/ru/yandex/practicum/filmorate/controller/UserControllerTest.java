package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.UserRequestException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class UserControllerTest {
    UserController userController;
    User user1;
    User user2;

    @BeforeEach
    void initialize() {
        userController = new UserController();
        user1 = User.builder().email("qwerty.@йцукен.123").login("Пользователь1").name("Иванов Иван")
                .birthday(LocalDate.of(2011, 1, 1)).build();
        user2 = User.builder().id(1).email("asdfgh.@фывапр.123").login("Пользователь2").name("Петров Пётр")
                .birthday(LocalDate.of(2012, 2, 2)).build();
    }

    @Test
    void createUser() {
        userController.createUser(user1);
        Assertions.assertEquals(user1, userController.getAllUsers().get(0));
    }

    @Test
    void updateUser() {
        userController.createUser(user1);
        userController.updateUser(user2);
        Assertions.assertEquals(user2, userController.getAllUsers().get(0));
    }

    @Test
    void checkUserWithIncorrectEmail() {
        User user = User.builder().id(1).email("qwerty.йцукен.123").login("Пользователь").name("Иванов Иван")
                .birthday(LocalDate.of(2011, 1, 1)).build();
        Exception exception = assertThrows(UserRequestException.class, () -> userController.createUser(user));
        Assertions.assertEquals("Адрес электронной почты не указан, либо указан некорректно.\n", exception.getMessage());
    }

    @Test
    void checkUserWithoutEmail() {
        User user = User.builder().id(1).login("Пользователь").name("Иванов Иван")
                .birthday(LocalDate.of(2011, 1, 1)).build();
        Exception exception = assertThrows(UserRequestException.class, () -> userController.createUser(user));
        Assertions.assertNull(user.getEmail());
        Assertions.assertEquals("Адрес электронной почты не указан, либо указан некорректно.\n", exception.getMessage());
    }

    @Test
    void checkUserWithIncorrectLogin() {
        User user = User.builder().id(1).email("qwerty.@йцукен.123").login("Поль зо ва тель").name("Иванов Иван")
                .birthday(LocalDate.of(2011, 1, 1)).build();
        Exception exception = assertThrows(UserRequestException.class, () -> userController.createUser(user));
        Assertions.assertEquals("Логин не указан, либо указан некорректно.\n", exception.getMessage());
    }

    @Test
    void checkUserWithoutLogin() {
        User user = User.builder().id(1).email("qwerty.@йцукен.123").name("Иванов Иван")
                .birthday(LocalDate.of(2011, 1, 1)).build();
        Exception exception = assertThrows(UserRequestException.class, () -> userController.createUser(user));
        Assertions.assertNull(user.getLogin());
        Assertions.assertEquals("Логин не указан, либо указан некорректно.\n", exception.getMessage());
    }
}
