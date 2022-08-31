package ru.yandex.practicum.filmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validators.ValidatorFilm;
import ru.yandex.practicum.filmorate.validators.ValidatorFilmId;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int id = 0;

    @Override
    public Film create(Film film) {
        ValidatorFilm.validate(film);
        film.setId(++id);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film delete(Film film) {
        ValidatorFilm.validate(film);
        return films.remove(film.getId());
    }

    @Override
    public Film get(int id) {
        ValidatorFilmId.validate(films, id);
        return films.get(id);
    }

    @Override
    public Film update(Film film) {
        ValidatorFilm.validate(film);
        ValidatorFilmId.validate(films, film);
        films.put(film.getId(), film);
        return film;
    }

    @Override
    public ArrayList<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Map<Integer, Film> getFilmsMap() {
        return films;
    }
}
