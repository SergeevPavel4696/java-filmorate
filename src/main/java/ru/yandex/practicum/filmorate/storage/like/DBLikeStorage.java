package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.film.DBFilmStorage;

@Component("likesStorage")
public class DBLikeStorage implements LikeStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    DBFilmStorage dbFilmStorage;

    @Override
    public Integer addLikes(int filmId, int meId) {
        String query = "INSERT INTO likes (user_id, film_id) VALUES (?, ?);";
        jdbcTemplate.update(query, filmId, meId);
        return dbFilmStorage.get(filmId).getLikes().size();
    }

    @Override
    public Integer deleteLikes(int filmId, int meId) {
        String query = "DELETE FROM likes WHERE film_id = ? AND user_id = ?;";
        jdbcTemplate.update(query, filmId, meId);
        return dbFilmStorage.get(meId).getLikes().size();
    }
}
