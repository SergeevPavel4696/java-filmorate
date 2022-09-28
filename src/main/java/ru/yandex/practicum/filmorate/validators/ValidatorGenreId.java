package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Map;

@Slf4j
public class ValidatorGenreId {
    public static void validate(Map<Integer, Genre> genres, Integer gereId) {
        if (!genres.containsKey(gereId)) {
            log.info("Жанр по указанному id не существует.");
            throw new IncorrectId("Жанр по указанному id не существует.");
        }
    }

    public static void validate(Map<Integer, Genre> films, Genre genre) {
        if (!films.containsKey(genre.getId())) {
            log.info("Такого жанра нет.");
            throw new IncorrectId("Такого жанра нет.");
        }
    }
}
