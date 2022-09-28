package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryLikeStorage implements LikeStorage {

    @Autowired
    FilmStorage filmStorage;

    @Override
    public Integer addLikes(int filmId, int meId) {
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.get(filmId).getLikes() != null) {
            friendsLikes = filmStorage.get(filmId).getLikes();
        }
        friendsLikes.add(meId);
        filmStorage.get(filmId).setLikes(friendsLikes);
        return filmStorage.get(filmId).getLikes().size();
    }

    @Override
    public Integer deleteLikes(int filmId, int meId) {
        Set<Integer> friendsLikes = new HashSet<>();
        if (filmStorage.get(filmId).getLikes() != null) {
            friendsLikes = filmStorage.get(filmId).getLikes();
        }
        friendsLikes.remove(meId);
        filmStorage.get(filmId).setLikes(friendsLikes);
        return filmStorage.get(filmId).getLikes().size();
    }
}
