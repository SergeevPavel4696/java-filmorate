package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validators.ValidatorUser;
import ru.yandex.practicum.filmorate.validators.ValidatorUserId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    UserStorage userStorage;

    public Integer addFriend(Integer meId, Integer friendId) {
        ValidatorUserId.validate(meId, friendId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
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

    public Integer deleteFriend(Integer meId, Integer friendId) {
        ValidatorUserId.validate(meId, friendId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
        ValidatorUserId.validate(userStorage.getUsersMap(), meId);
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

    public List<User> getFriends(Integer userId) {
        ValidatorUserId.validate(userStorage.getUsersMap(), userId);
        List<User> friends = new ArrayList<>();
        for (int userFriendId : userStorage.get(userId).getFriends()) {
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
}
