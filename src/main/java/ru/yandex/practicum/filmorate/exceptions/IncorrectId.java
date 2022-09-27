package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectId extends RuntimeException {
    public IncorrectId(String message) {
        super(message);
    }
}
