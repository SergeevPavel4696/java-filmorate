package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    @Autowired
    InMemoryUserStorage userStorage;
    UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userStorage.createUser(user);
    }

    @DeleteMapping
    public User deleteUser(@RequestBody User user) {
        return userStorage.deleteUser(user);
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        return userStorage.updateUser(user);
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable("userId") Integer userId) {
        return userStorage.getUser(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    @PutMapping("/{meId}/friends/{friendId}")
    public User addFriend(@PathVariable("meId") Integer meId, @PathVariable("friendId") Integer friendId) {
        return userService.addFriend(userStorage.getUsersMap().get(meId), userStorage.getUsersMap().get(friendId));
    }

    @DeleteMapping("/{meId}/friends/{friendId}")
    public User deleteFriend(@PathVariable("meId") Integer meId, @PathVariable("friendId") Integer friendId) {
        return userService.deleteFriend(userStorage.getUsersMap().get(meId), userStorage.getUsersMap().get(friendId));
    }

    @GetMapping("/{userId}/friends")
    public List<User> getFriends(@PathVariable("userId") Integer userId) {
        return userService.getFriends(userStorage.getUsersMap().get(userId));
    }

    @GetMapping("/{meId}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable("meId") Integer meId, @PathVariable("otherId") Integer friendId) {
        return userService.getCommonFriends(userStorage.getUsersMap().get(meId), userStorage.getUsersMap().get(friendId));
    }
}
