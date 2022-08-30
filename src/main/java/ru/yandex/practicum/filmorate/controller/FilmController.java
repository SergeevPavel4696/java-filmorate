package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
    @Autowired
    InMemoryUserStorage userStorage;
    @Autowired
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
