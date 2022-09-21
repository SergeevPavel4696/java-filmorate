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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {

    @Autowired
    FilmStorage filmStorage;
    @Autowired
    UserStorage userStorage;
    @Autowired
    FilmService filmService;

    @PostMapping
    public Film createFilm(@RequestBody Film film) {
        return filmStorage.create(film);
    }

    @DeleteMapping
    public Film deleteFilm(@RequestBody Film film) {
        return filmStorage.delete(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        return filmStorage.update(film);
    }

    @GetMapping("/{filmId}")
    public Film getUser(@PathVariable("filmId") Integer filmId) {
        return filmStorage.get(filmId);
    }

    @GetMapping
    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    @PutMapping("/{filmId}/like/{meId}")
    public Integer addLike(@PathVariable("filmId") Integer filmId, @PathVariable("meId") Integer meId) {
        return filmService.addLike(filmId, meId);
    }

    @DeleteMapping("/{filmId}/like/{meId}")
    public Integer deleteLike(@PathVariable("filmId") Integer filmId, @PathVariable("meId") Integer meId) {
        return filmService.deleteLike(filmId, meId);
    }

    @GetMapping("/popular")
    public List<Film> getMostPopularFilms(@RequestParam(value = "count", required = false) Integer count) {
        return filmService.getMostPopularFilms(count);
    }
}
