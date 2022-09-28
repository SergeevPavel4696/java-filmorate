package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FilmStorage {

    Film create(Film film);

    Film delete(Film film);

    Film get(int filmId);

    Film update(Film film);

    List<Film> getAll();

    Map<Integer, Film> getFilmsMap();

    Set<Integer> getFilmLikes(int filmId);
}
