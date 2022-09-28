package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.storage.mpa.MPAStorage;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MPAService {

    @Autowired
    private final MPAStorage mpaStorage;

    public MPA get(int id) {
        return mpaStorage.get(id);
    }

    public List<MPA> getAll() {
        return mpaStorage.getAll();
    }

    public MPA getByFilmId(int filmId) {
        return mpaStorage.getByFilmId(filmId);
    }
}
