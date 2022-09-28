package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.GenreService;
import ru.yandex.practicum.filmorate.service.MPAService;
import ru.yandex.practicum.filmorate.validators.ValidatorFilm;
import ru.yandex.practicum.filmorate.validators.ValidatorFilmId;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component("filmStorage")
public class DBFilmStorage implements FilmStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    GenreService genreService;
    @Autowired
    MPAService mpaService;

    @Override
    public Film create(Film film) {
        ValidatorFilm.validate(film);
        String query = "INSERT INTO films (name, description, release_date, duration, mpa_id) VALUES (?, ?, ?, ?, ?);";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(query, new String[]{"id"});
            statement.setString(1, film.getName());
            statement.setString(2, film.getDescription());
            statement.setDate(3, Date.valueOf(film.getReleaseDate()));
            statement.setInt(4, film.getDuration());
            statement.setInt(5, film.getMpa().getId());
            return statement;
        }, keyHolder);
        film.setId(Objects.requireNonNull(keyHolder.getKey()).intValue());
        return film;
    }

    @Override
    public Film delete(Film film) {
        ValidatorFilm.validate(film);
        String query = "DELETE films WHERE id = ?;";
        jdbcTemplate.update(query, film.getId());
        return film;
    }

    @Override
    public Film get(int filmId) {
        ValidatorFilmId.validate(getFilmsMap(), filmId);
        String query = "SELECT * FROM films WHERE id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeFilm(rs), filmId)
                .stream()
                .findAny()
                .orElseThrow(() -> new IncorrectId("Фильм по указанному id не существует."));
    }

    @Override
    public Film update(Film film) {
        ValidatorFilm.validate(film);
        ValidatorFilmId.validate(getFilmsMap(), film);
        String query = "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? " +
                "WHERE id = ?;";
        jdbcTemplate.update(query, film.getName(), film.getDescription(), film.getReleaseDate(),
                film.getDuration(), film.getMpa().getId(), film.getId());
        return get(film.getId());
    }

    @Override
    public List<Film> getAll() {
        String query = "SELECT * FROM films;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeFilm(rs));
    }

    @Override
    public Map<Integer, Film> getFilmsMap() {
        List<Film> filmsList = getAll();
        Map<Integer, Film> filmsMap = new HashMap<>();
        for (Film film : filmsList) {
            filmsMap.put(film.getId(), film);
        }
        return filmsMap;
    }

    @Override
    public Set<Integer> getFilmLikes(int filmId) {
        String query = "SELECT user_id FROM likes WHERE film_id = ?;";
        return new HashSet<>(jdbcTemplate.queryForList(query, Integer.class, filmId));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String description = rs.getString("description");
        LocalDate releaseDate = rs.getDate("release_date").toLocalDate();
        int duration = rs.getInt("duration");
        Set<Integer> likes = getFilmLikes(id);
        Set<Genre> genres = genreService.getByFilmId(id);
        MPA mpa = mpaService.getByFilmId(id);
        return new Film(id, name, description, releaseDate, duration, likes, genres, mpa);
    }
}
