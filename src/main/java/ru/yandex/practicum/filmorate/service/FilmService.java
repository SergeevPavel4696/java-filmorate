package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorFilmId;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.*;

@Service
@RequiredArgsConstructor
public class FilmService {

    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;

    public Integer addLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.getFilm(filmId).getLikes() != null) {
            friendsLikes = filmStorage.getFilm(filmId).getLikes();
        }
        friendsLikes.add(meId);
        filmStorage.getFilm(filmId).setLikes(friendsLikes);
        return filmStorage.getFilm(filmId).getLikes().size();
    }

    public Integer deleteLike(Integer filmId, Integer meId) {
        ValidatorFilmId.validate(filmStorage.getFilmsMap(), filmId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.getFilm(filmId).getLikes() != null) {
            friendsLikes = filmStorage.getFilm(filmId).getLikes();
        }
        friendsLikes.remove(meId);
        filmStorage.getFilm(filmId).setLikes(friendsLikes);
        return filmStorage.getFilm(filmId).getLikes().size();
    }

    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getAllFilms());
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
}
