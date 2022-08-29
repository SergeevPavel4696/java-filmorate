package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.FilmRequestException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    private void validateFilm(Film film) {
        String incorrect = "";
        if (film == null) {
            log.info("Вы передали пустой фильм");
            throw new FilmRequestException("Вы передали пустой фильм");
        }
        if (film.getName() == null || film.getName().isEmpty()) {
            incorrect = incorrect + "Название фильма не указано.\n";
        }
        if (film.getDescription() != null && film.getDescription().length() > 200) {
            incorrect = incorrect + "Описание фильма слишком длинное.\n";
        }
        if (film.getReleaseDate() != null && film.getReleaseDate().isBefore(LocalDate.of(1895, 12, 28))) {
            incorrect = incorrect + "Тогда фильмы ещё не снимали.\n";
        }
        if (film.getDuration() != null && film.getDuration() < 0) {
            incorrect = incorrect + "Продолжительность фильма указана некорректно.\n";
        }
        if (!incorrect.isEmpty()) {
            log.info(incorrect);
            throw new FilmRequestException(incorrect);
        }
    }

    private void validateId(Integer id) {
        if (id == null) {
            log.info("Вы передали пустой id");
            throw new FilmRequestException("Вы передали пустой id");
        }
        if (!films.containsKey(id)) {
            log.info("Фильм по указанному id не существует.\n");
            throw new FilmRequestException("Фильм по указанному id не существует.\n");
        }
    }

    @Override
    public Film createFilm(Film film) {
        validateFilm(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film deleteFilm(Film film) {
        validateId(film.getId());
        return films.remove(film.getId());
    }

    @Override
    public Film getFilm(int id) {
        validateId(id);
        return films.get(id);
    }

    @Override
    public Film updateFilm(Film film) {
        validateFilm(film);
        if (!films.containsKey(film.getId())) {
            log.info("Такого фильма нет.");
            throw new FilmRequestException("Такого фильма нет.");
        }
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    public Map<Integer, Film> getFilmsMap() {
        return films;
    }
}
