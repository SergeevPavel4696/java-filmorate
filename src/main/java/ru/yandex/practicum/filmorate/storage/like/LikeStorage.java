package ru.yandex.practicum.filmorate.storage.like;

public interface LikeStorage {

    Integer addLikes(int filmId, int meId);

    Integer deleteLikes(int filmId, int meId);
}
