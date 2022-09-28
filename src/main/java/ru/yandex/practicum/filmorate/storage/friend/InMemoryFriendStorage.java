package ru.yandex.practicum.filmorate.storage.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.HashSet;
import java.util.Set;

@Component
public class InMemoryFriendStorage implements FriendStorage {

    @Autowired
    UserStorage userStorage;

    @Override
    public Integer addFriend(int meId, int friendId) {
        Set<Integer> myFriends = new HashSet<>();
        Set<Integer> friendsFriends = new HashSet<>();
        if (userStorage.get(meId).getFriends() != null) {
            myFriends = userStorage.get(meId).getFriends();
        }
        if (userStorage.get(friendId).getFriends() != null) {
            friendsFriends = userStorage.get(friendId).getFriends();
        }
        myFriends.add(friendId);
        friendsFriends.add(meId);
        userStorage.get(meId).setFriends(myFriends);
        userStorage.get(friendId).setFriends(friendsFriends);
        return userStorage.get(meId).getFriends().size();
    }

    @Override
    public Integer deleteFriend(int meId, int friendId) {
        Set<Integer> myFriends = new HashSet<>();
        Set<Integer> friendsFriends = new HashSet<>();
        if (userStorage.get(meId).getFriends() != null) {
            myFriends = userStorage.get(meId).getFriends();
        }
        if (userStorage.get(friendId).getFriends() != null) {
            friendsFriends = userStorage.get(friendId).getFriends();
        }
        myFriends.remove(friendId);
        friendsFriends.remove(meId);
        userStorage.get(meId).setFriends(myFriends);
        userStorage.get(friendId).setFriends(friendsFriends);
        return userStorage.get(meId).getFriends().size();
    }
}
