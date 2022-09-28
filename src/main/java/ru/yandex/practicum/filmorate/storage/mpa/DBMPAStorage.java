package ru.yandex.practicum.filmorate.storage.mpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.IncorrectId;
import ru.yandex.practicum.filmorate.model.MPA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DBMPAStorage implements MPAStorage {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public MPA get(int mpaId) {
        String query = "SELECT id, name FROM mpa_ratings WHERE id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeMPA(rs), mpaId)
                .stream()
                .findAny()
                .orElseThrow(() -> new IncorrectId("Рейтинг по указанному id не существует."));
    }

    @Override
    public List<MPA> getAll() {
        String query = "SELECT id, name FROM mpa_ratings;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeMPA(rs));
    }

    @Override
    public MPA getByFilmId(int filmId) {
        String query = "SELECT m.id, m.name FROM mpa_ratings AS m JOIN films AS f ON f.mpa_id = m.id WHERE f.id = ?;";
        return jdbcTemplate.query(query, (rs, rowNum) -> makeMPA(rs), filmId)
                .stream()
                .findAny()
                .orElseThrow(() -> new IncorrectId("Фильм по указанному id не существует."));
    }

    private MPA makeMPA(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        return new MPA(id, name);
    }
}
