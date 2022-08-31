package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.Map;

public interface FilmStorage {

    Film create(Film film);

    Film delete(Film film);

    Film get(int id);

    Film update(Film film);

    ArrayList<Film> getAllFilms();

    Map<Integer, Film> getFilmsMap();
}
