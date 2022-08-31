package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserStorage userStorage;
    @Autowired
    UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userStorage.create(user);
    }

    @DeleteMapping
    public User deleteUser(@RequestBody User user) {
        return userStorage.delete(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userStorage.update(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userStorage.get(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @PutMapping("/{meId}/friends/{friendId}")
    public Integer addFriend(@PathVariable("meId") Integer meId, @PathVariable("friendId") Integer friendId) {
        return userService.addFriend(meId, friendId);
    }

    @DeleteMapping("/{meId}/friends/{friendId}")
    public Integer deleteFriend(@PathVariable("meId") Integer meId, @PathVariable("friendId") Integer friendId) {
        return userService.deleteFriend(meId, friendId);
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable("userId") Integer userId) {
        return userService.getFriends(userId);
    }

    @GetMapping("/{meId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("meId") Integer meId, @PathVariable("otherId") Integer friendId) {
        return userService.getCommonFriends(userStorage.get(meId), userStorage.get(friendId));
    }
}
