package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.exceptions.FilmRequestException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Integer, Film> films = new HashMap<>();

    private int id = 0;

    private void isFilm(Film film) {
        String incorrect = "";
        if (film.getName() == null || film.getName().isEmpty()) {
            incorrect = incorrect + "Название фильма не указано.\n";
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            incorrect = incorrect + "Описание фильма слишком длинное.\n";
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            incorrect = incorrect + "Тогда фильмы ещё не снимали.\n";
        }
        if (film.getDuration() != null && film.getDuration().isNegative()) {
            incorrect = incorrect + "Продолжительность фильма указана некорректно.\n";
        }
        if (!incorrect.isEmpty()) {
            log.info(incorrect);
            throw new FilmRequestException(incorrect);
        }
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) {
        isFilm(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) {
        isFilm(film);
        films.put(film.getId(), film);
        return film;
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }
}
