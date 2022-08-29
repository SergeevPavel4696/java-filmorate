package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    UserStorage userStorage;

    public User addFriend(User me, User friend) {
        if (me.getId() != friend.getId()) {
            Set<Integer> myFriends = me.getFriends();
            Set<Integer> friendsFriends = friend.getFriends();
            myFriends.add(friend.getId());
            friendsFriends.add(me.getId());
            me.setFriends(myFriends);
            friend.setFriends(friendsFriends);
        }
        return friend;
    }

    public User deleteFriend(User me, User friend) {
        if (me.getFriends().contains(friend.getId())) {
            Set<Integer> myFriends = me.getFriends();
            Set<Integer> friendsFriends = friend.getFriends();
            myFriends.remove(friend.getId());
            friendsFriends.remove(me.getId());
            me.setFriends(myFriends);
            friend.setFriends(friendsFriends);
        }
        return friend;
    }

    public List<User> getFriends(User user) {
        List<User> friends = new ArrayList<>();
        for (int userFriendId : user.getFriends()) {
            friends.add(userStorage.getUsersMap().get(userFriendId));
        }
        return friends;
    }

    public List<User> getCommonFriends(User me, User user) {
        List<User> mutualFriends = new ArrayList<>();
        if (me.getFriends().size() > user.getFriends().size()) {
            for (int userFriendId : user.getFriends()) {
                if (me.getFriends().contains(userFriendId)) {
                    mutualFriends.add(userStorage.getUsersMap().get(userFriendId));
                }
            }
        } else {
            for (int meFriendId : me.getFriends()) {
                if (user.getFriends().contains(meFriendId)) {
                    mutualFriends.add(userStorage.getUsersMap().get(meFriendId));
                }
            }
        }
        return mutualFriends;
    }
}
