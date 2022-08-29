package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    @Autowired
    InMemoryFilmStorage filmStorage;
    InMemoryUserStorage userStorage;
    FilmService filmService;

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmStorage.createFilm(film);
    }

    @DeleteMapping
    public Film deleteFilm(@RequestBody Film film) {
        return filmStorage.deleteFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmStorage.updateFilm(film);
    }

    @GetMapping("/{filmId}")
    public Film getUser(@PathVariable("filmId") Integer filmId) {
        return filmStorage.getFilm(filmId);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @PutMapping("/{filmId}/friends/{meId}")
    public Film addLike(@PathVariable("filmId") Integer filmId, @PathVariable("meId") Integer meId) {
        return filmService.addLike(filmStorage.getFilmsMap().get(filmId), userStorage.getUsersMap().get(meId));
    }

    @DeleteMapping("/{filmId}/friends/{meId}")
    public Film deleteLike(@PathVariable("filmId") Integer filmId, @PathVariable("meId") Integer meId) {
        return filmService.deleteLike(filmStorage.getFilmsMap().get(filmId), userStorage.getUsersMap().get(meId));
    }

    @GetMapping("/popular?count={count}")
    public List<Film> getCommonFriends(@PathVariable("count") Integer count) {
        return filmService.getMostPopularFilms(count);
    }
}
