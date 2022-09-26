package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorFilmId;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    private final FilmStorage filmStorage;
    @Autowired
    private final UserStorage userStorage;

    public Integer addLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.get(filmId).getLikes() != null) {
            friendsLikes = filmStorage.get(filmId).getLikes();
        }
        friendsLikes.add(meId);
        filmStorage.get(filmId).setLikes(friendsLikes);
        return filmStorage.get(filmId).getLikes().size();
    }

    public Integer deleteLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.get(filmId).getLikes() != null) {
            friendsLikes = filmStorage.get(filmId).getLikes();
        }
        friendsLikes.remove(meId);
        filmStorage.get(filmId).setLikes(friendsLikes);
        return filmStorage.get(filmId).getLikes().size();
    }

    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getAll());
        mostPopularFilms.sort((f1, f2) -> {
            if (f1.getLikes() == null) {
                return 1;
            } else if (f2.getLikes() == null) {
                return -1;
            } else {
                return f1.getLikes().size() - f2.getLikes().size();
            }
        });
        if (count == null && mostPopularFilms.size() > 10) {
            return mostPopularFilms.subList(0, 10);
        } else if (count == null) {
            return mostPopularFilms;
        } else if (count > mostPopularFilms.size()) {
            return mostPopularFilms;
        } else {
            return mostPopularFilms.subList(0, count);
        }
    }

    public Film create(Film film) {
        return filmStorage.create(film);
    }

    public Film delete(Film film) {
        return filmStorage.delete(film);
    }

    public Film update(Film film) {
        return filmStorage.update(film);
    }

    public Film get(int filmId) {
        return filmStorage.get(filmId);
    }

    public List<Film> getAll() {
        return filmStorage.getAll();
    }
}
