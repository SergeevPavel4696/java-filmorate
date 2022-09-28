package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.validators.ValidatorGenreId;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DBGenreStorage implements GenreStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Genre get(int genreId) {
        ValidatorGenreId.validate(getGenresMap(), genreId);
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
    public Set<Genre> getByFilmId(int filmId) {
        String query = "SELECT g.id, g.name FROM genres AS g JOIN films_genres AS fg ON fg.genre_id = g.id " +
                "WHERE fg.film_id = ?;";
        return new HashSet<>(jdbcTemplate.query(query, (rs, rowNum) -> makeGenre(rs), filmId));
    }

    @Override
    public Map<Integer, Genre> getGenresMap() {
        List<Genre> genreList = getAll();
        Map<Integer, Genre> genresMap = new HashMap<>();
        for (Genre genre : genreList) {
            genresMap.put(genre.getId(), genre);
        }
        return genresMap;
    }

    @Override
    public void addGenresToFilm(int filmId, Set<Genre> genres) {
        ArrayList<Genre> genresDistinct = new ArrayList<>(genres);
        genresDistinct.sort(Comparator.comparingInt(Genre::getId));
        jdbcTemplate.batchUpdate(
                "INSERT INTO films_genres (genre_id, film_id) VALUES (?, ?);",
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement statement, int i) throws SQLException {
                        statement.setInt(1, genresDistinct.get(i).getId());
                        statement.setInt(2, filmId);
                    }
                    public int getBatchSize() {
                        return genresDistinct.size();
                    }
                }
        );
    }

    @Override
    public void deleteGenresFromFilm(int filmId) {
        String query = "DELETE FROM films_genres WHERE film_id = ?;";
        jdbcTemplate.update(query, filmId);
    }

    private Genre makeGenre(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new Genre(id, name);
    }
}
