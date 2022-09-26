package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DBGenreStorage implements GenreStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(int genreId) {
        String query = "SELECT id, name FROM genres WHERE id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeGenre(rs), genreId)
                .stream()
                .findAny()
                .orElseThrow(() -> new IncorrectId("Жанр по указанному id не существует."));
    }

    @Override
    public List<Genre> getAll() {
        String query = "SELECT id, name FROM genres;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeGenre(rs));
    }

    @Override
    public List<Genre> getByFilmId(int filmId) {
        String query = "SELECT g.id, g.name FROM genres AS g JOIN films_genres AS fg ON fg.genre_id = g.id" +
                "WHERE fg.film_id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeGenre(rs), filmId);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
