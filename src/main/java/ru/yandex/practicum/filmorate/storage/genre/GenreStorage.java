package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreStorage {

    Genre get(int genreId);

    List<Genre> getAll();

    List<Genre> getByFilmId(int filmId);
}
