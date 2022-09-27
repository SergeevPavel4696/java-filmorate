package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {

    @Autowired
    private final GenreStorage genreStorage;

    public Genre get(int id) {
        return genreStorage.get(id);
    }

    public List<Genre> getAll() {
        return genreStorage.getAll();
    }

    public Set<Genre> getByFilmId(int filmId) {
        return genreStorage.getByFilmId(filmId);
    }

    public void updateForFilm(int filmId, Set<Genre> genres) {
        genreStorage.deleteGenresFromFilm(filmId);
        genreStorage.addGenresToFilm(filmId, genres);
    }
}
