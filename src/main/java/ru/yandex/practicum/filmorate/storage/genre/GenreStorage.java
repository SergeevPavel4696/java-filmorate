package ru.yandex.practicum.filmorate.storage.genre;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface GenreStorage {

    Genre get(int genreId);

    List<Genre> getAll();

    Set<Genre> getByFilmId(int filmId);

    Map<Integer, Genre> getGenresMap();

    void addGenresToFilm(int filmId, Set<Genre> genres);

    void deleteGenresFromFilm(int filmId);
}
