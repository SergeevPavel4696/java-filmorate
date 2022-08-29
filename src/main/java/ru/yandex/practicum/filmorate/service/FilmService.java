package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class FilmService {

    FilmStorage filmStorage;

    public Film addLike(Film film, User me) {
        if (!film.getLikes().contains(me.getId())) {
            Set<Integer> likes = film.getLikes();
            likes.add(me.getId());
            film.setLikes(likes);
        }
        return film;
    }

    public Film deleteLike(Film film, User me) {
        if (film.getLikes().contains(me.getId())) {
            Set<Integer> likes = film.getLikes();
            likes.remove(me.getId());
            film.setLikes(likes);
        }
        return film;
    }

    public List<Film> getMostPopularFilms(Integer count) {
        List<Film> mostPopularFilms = new ArrayList<>(filmStorage.getAllFilms());
        mostPopularFilms.sort(Comparator.comparingInt(f -> f.getLikes().size()));
        if (count == null && mostPopularFilms.size() > 10) {
            return mostPopularFilms.subList(0, 10);
        } else if (count == null) {
            return mostPopularFilms;
        } else if (count > mostPopularFilms.size()) {
            return mostPopularFilms;
        } else {
            return mostPopularFilms.subList(0, count - 1);
        }
    }
}
