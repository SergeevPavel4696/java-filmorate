package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;

import java.util.Map;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice({
        "ru.yandex.practicum.filmorate.controller",
        "ru.yandex.practicum.filmorate.service",
        "ru.yandex.practicum.filmorate.storage"
})
public class ErrorController {

    @ExceptionHandler
    @ResponseStatus(NOT_FOUND)
    public Map<String, String> handleIncorrectId(IncorrectId e) {
        return Map.of("Error", e.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public Map<String, String> handleValidation(ValidationException e) {
        return Map.of("Error", e.getMessage());
    }
}
