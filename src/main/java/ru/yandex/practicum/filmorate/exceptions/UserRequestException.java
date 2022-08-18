package ru.yandex.practicum.filmorate.exceptions;

public class UserRequestException extends RuntimeException {
    public UserRequestException() {}

    public UserRequestException(String message) {
        super(message);
    }
}
