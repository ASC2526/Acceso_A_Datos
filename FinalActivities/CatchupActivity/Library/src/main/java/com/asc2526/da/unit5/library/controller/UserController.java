package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{code}")
    public User getUserById(@PathVariable String code) {
        return userService.getUserById(code);
    }

    @PostMapping
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{code}")
    public void deleteUser(@PathVariable String code) {
        userService.deleteUser(code);
    }
}