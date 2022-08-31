package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Map;

@Slf4j
public class ValidatorFilmId {
    public static void validate(java.util.Map<Integer, Film> films, Integer filmId) {
        if (!films.containsKey(filmId)) {
            log.info("Фильм по указанному id не существует.");
            throw new IncorrectId("Фильм по указанному id не существует.");
        }
    }

    public static void validate(Map<Integer, Film> films, Film film) {
        if (!films.containsKey(film.getId())) {
            log.info("Такого фильма нет.");
            throw new IncorrectId("Такого фильма нет.");
        }
    }
}
