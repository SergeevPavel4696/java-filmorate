package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserStorage {

    private final Map<Integer, User> users = new HashMap<>();
    private int id = 0;

    @Override
    public User create(User user) {
        ValidatorUser.validate(user);
        user.setId(++id);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User delete(User user) {
        ValidatorUser.validate(user);
        return users.remove(user.getId());
    }

    @Override
    public User get(int userId) {
        ValidatorUserId.validate(users, userId);
        return users.get(userId);
    }

    @Override
    public User update(User user) {
        ValidatorUser.validate(user);
        ValidatorUserId.validate(users, user);
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public ArrayList<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Map<Integer, User> getUsersMap() {
        return users;
    }

    @Override
    public Set<Integer> getUserFriends(int userId) {
        ValidatorUserId.validate(getUsersMap(), userId);
        return get(userId).getFriends();
    }

    @Override
    public Integer addFriend(int meId, int friendId) {
        Set<Integer> myFriends = new HashSet<>();
        Set<Integer> friendsFriends = new HashSet<>();
        if (get(meId).getFriends() != null) {
            myFriends = get(meId).getFriends();
        }
        if (get(friendId).getFriends() != null) {
            friendsFriends = get(friendId).getFriends();
        }
        myFriends.add(friendId);
        friendsFriends.add(meId);
        get(meId).setFriends(myFriends);
        get(friendId).setFriends(friendsFriends);
        return get(meId).getFriends().size();
    }

    @Override
    public Integer deleteFriend(int meId, int friendId) {
        Set<Integer> myFriends = new HashSet<>();
        Set<Integer> friendsFriends = new HashSet<>();
        if (get(meId).getFriends() != null) {
            myFriends = get(meId).getFriends();
        }
        if (get(friendId).getFriends() != null) {
            friendsFriends = get(friendId).getFriends();
        }
        myFriends.remove(friendId);
        friendsFriends.remove(meId);
        get(meId).setFriends(myFriends);
        get(friendId).setFriends(friendsFriends);
        return get(meId).getFriends().size();
    }
}
