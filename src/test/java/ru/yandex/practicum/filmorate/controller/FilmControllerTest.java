package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exceptions.FilmRequestException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

class FilmControllerTest {
    FilmController filmController;
    Film film1;
    Film film2;

    @BeforeEach
    void initialize() {
        filmController = new FilmController();
        film1 = Film.builder().id(1).name("Фильм 1").description("Описание фильма 1")
                .releaseDate(LocalDate.of(2011, 1, 1)).duration(Duration.ofMinutes(111)).build();
        film2 = Film.builder().id(1).name("Фильм 2").description("Описание фильма 2")
                .releaseDate(LocalDate.of(2012, 2, 2)).duration(Duration.ofMinutes(222)).build();
    }

    @Test
    void createFilm() {
        filmController.createFilm(film1);
        Assertions.assertEquals(film1, filmController.getAllFilms().get(0));
    }

    @Test
    void updateFilm() {
        filmController.createFilm(film1);
        filmController.updateFilm(film2);
        Assertions.assertEquals(film2, filmController.getAllFilms().get(0));
    }

    @Test
    void checkFilmWithIncorrectName() {
        Film film = Film.builder().id(1).name("").description("Описание фильма 1")
                .releaseDate(LocalDate.of(2011, 1, 1)).duration(Duration.ofMinutes(111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertEquals("Название фильма не указано.\n", exception.getMessage());
    }

    @Test
    void checkFilmWithoutName() {
        Film film = Film.builder().id(1).description("Описание фильма 1")
                .releaseDate(LocalDate.of(2011, 1, 1)).duration(Duration.ofMinutes(111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertNull(film.getName());
        Assertions.assertEquals("Название фильма не указано.\n", exception.getMessage());
    }

    @Test
    void checkFilmWithIncorrectDescription() {
        Film film = Film.builder().id(1).name("Фильм").description(
                        "111111112222222233333333444444445555555566666666777777778888888899999999" +
                                "111111112222222233333333444444445555555566666666777777778888888899999999" +
                                "111111112222222233333333444444445555555566666666777777778888888899999999")
                .releaseDate(LocalDate.of(2011, 1, 1)).duration(Duration.ofMinutes(111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertEquals("Описание фильма слишком длинное.\n", exception.getMessage());
    }

    @Test
    void checkFilmWithIncorrectReleaseDate() {
        Film film = Film.builder().id(1).name("Фильм").description("Описание фильма 1")
                .releaseDate(LocalDate.of(500, 1, 1)).duration(Duration.ofMinutes(111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertEquals("Тогда фильмы ещё не снимали.\n", exception.getMessage());
    }

    @Test
    void checkFilmWithIncorrectDuration() {
        Film film = Film.builder().id(1).name("Фильм").description("Описание фильма 1")
                .releaseDate(LocalDate.of(2011, 1, 1)).duration(Duration.ofMinutes(-111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertEquals("Продолжительность фильма указана некорректно.\n", exception.getMessage());
    }

    @Test
    void checkIncorrectFilm() {
        Film film = Film.builder().description(
                        "111111112222222233333333444444445555555566666666777777778888888899999999" +
                                "111111112222222233333333444444445555555566666666777777778888888899999999" +
                                "111111112222222233333333444444445555555566666666777777778888888899999999")
                .releaseDate(LocalDate.of(500, 1, 1))
                .duration(Duration.ofMinutes(-111)).build();
        Exception exception = assertThrows(FilmRequestException.class, () -> filmController.createFilm(film));
        Assertions.assertEquals("Название фильма не указано.\n" +
                "Описание фильма слишком длинное.\nТогда фильмы ещё не снимали.\n" +
                "Продолжительность фильма указана некорректно.\n", exception.getMessage());
    }
}
