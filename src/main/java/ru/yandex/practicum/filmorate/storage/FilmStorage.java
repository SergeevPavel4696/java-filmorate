package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;

public interface FilmStorage {

    Film createFilm(Film film);

    Film deleteFilm(Film film);

    Film getFilm(int id);

    Film updateFilm(Film film);

    ArrayList<Film> getAllFilms();
}
