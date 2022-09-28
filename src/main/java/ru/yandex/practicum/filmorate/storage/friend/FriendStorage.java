package ru.yandex.practicum.filmorate.storage.friend;

public interface FriendStorage {

    Integer addFriend(int meId, int friendId);

    Integer deleteFriend(int meId, int friendId);
}
