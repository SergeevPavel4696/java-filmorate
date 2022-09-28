package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorFilm;
import ru.yandex.practicum.filmorate.validators.ValidatorFilmId;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;
    @Autowired
    private final LikeStorage likesStorage;
    @Autowired
    private final GenreService genreService;

    public Integer addLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        return likesStorage.addLikes(filmId, meId);
    }

    public Integer deleteLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        return likesStorage.deleteLikes(filmId, meId);
    }

    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getAll());
        mostPopularFilms.sort((f1, f2) -> {
            if (filmStorage.getFilmLikes(f1.getId()) == null) {
                return 1;
            } else if (filmStorage.getFilmLikes(f2.getId()) == null) {
                return -1;
            } else {
                return filmStorage.getFilmLikes(f1.getId()).size() - filmStorage.getFilmLikes(f2.getId()).size();
            }
        });
        if (count == null && mostPopularFilms.size() > 10) {
            return mostPopularFilms.subList(0, 10);
        } else if (count == null || count > mostPopularFilms.size()) {
            return mostPopularFilms;
        } else {
            return mostPopularFilms.subList(0, count);
        }
    }

    public Film create(Film film) {
        ValidatorFilm.validate(film);
        Film newFilm = filmStorage.create(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(newFilm.getId(), film.getGenres());
        }
        return newFilm;
    }

    public Film delete(Film film) {
        ValidatorFilm.validate(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(film.getId(), film.getGenres());
        }
        return filmStorage.delete(film);
    }

    public Film update(Film film) {
        ValidatorFilm.validate(film);
        if (film.getGenres() != null) {
            genreService.updateForFilm(film.getId(), film.getGenres());
        }
        return filmStorage.update(film);
    }

    public Film get(int filmId) {
        return filmStorage.get(filmId);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }
}
