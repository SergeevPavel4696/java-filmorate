package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.MPA;
import ru.yandex.practicum.filmorate.service.MPAService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class MPAController {

    @Autowired
    MPAService mpaService;

    @GetMapping("/{id}")
    public MPA findById(@PathVariable int id) {
        return mpaService.get(id);
    }

    @GetMapping
    public List<MPA> findAll() {
        return mpaService.getAll();
    }
}
