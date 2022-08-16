package ru.yandex.practicum.filmorate.exceptions;

public class FilmRequestException extends RuntimeException {
    public FilmRequestException(String message) {
        super(message);
    }
}
