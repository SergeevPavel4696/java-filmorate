package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friend.FriendStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private final UserStorage userStorage;
    @Autowired
    FriendStorage friendStorage;

    public Integer addFriend(Integer meId, Integer friendId) {
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        ValidatorUserId.validate(userStorage.getUsersMap(), friendId);
        ValidatorUserId.validate(meId, friendId);
        return friendStorage.addFriend(meId, friendId);
    }

    public Integer deleteFriend(Integer meId, Integer friendId) {
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        ValidatorUserId.validate(userStorage.getUsersMap(), friendId);
        ValidatorUserId.validate(meId, friendId);
        return friendStorage.deleteFriend(meId, friendId);
    }

    public List<User> getFriends(Integer userId) {
        ValidatorUserId.validate(userStorage.getUsersMap(), userId);
        List<User> friends = new ArrayList<>();
        for (int userFriendId : userStorage.getUserFriends(userId)) {
            friends.add(userStorage.get(userFriendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(User me, User user) {
        ValidatorUser.validate(me);
        ValidatorUser.validate(user);
        List<User> mutualFriends = new ArrayList<>();
        if (me.getFriends() == null || user.getFriends() == null) {
            return mutualFriends;
        }
        if (me.getFriends().size() > user.getFriends().size()) {
            for (int userFriendId : user.getFriends()) {
                if (me.getFriends().contains(userFriendId)) {
                    mutualFriends.add(userStorage.get(userFriendId));
                }
            }
        } else {
            for (int meFriendId : me.getFriends()) {
                if (user.getFriends().contains(meFriendId)) {
                    mutualFriends.add(userStorage.get(meFriendId));
                }
            }
        }
        return mutualFriends;
    }

    public User create(User user) {
        return userStorage.create(user);
    }

    public User delete(User user) {
        return userStorage.delete(user);
    }

    public User update(User user) {
        return userStorage.update(user);
    }

    public User get(int userId) {
        return userStorage.get(userId);
    }

    public List<User> getAll() {
        return userStorage.getAll();
    }
}
