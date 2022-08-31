package ru.yandex.practicum.filmorate.validators;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class ValidatorFilm {
    public static void validate(Film film) {
        if (film == null) {
            log.info("Вы передали пустой фильм");
            throw new ValidationException("Вы передали пустой фильм");
        }
        if (film.getName() == null || film.getName().isEmpty()) {
            log.info("Название фильма не указано.");
            throw new ValidationException("Название фильма не указано.");
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            log.info("Описание фильма слишком длинное.");
            throw new ValidationException("Описание фильма слишком длинное.");
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            log.info("Тогда фильмы ещё не снимали.");
            throw new ValidationException("Тогда фильмы ещё не снимали.");
        }
        if (film.getDuration() != null && film.getDuration() < 0) {
            log.info("Продолжительность фильма указана некорректно.");
            throw new ValidationException("Продолжительность фильма указана некорректно.");
        }
    }
}
