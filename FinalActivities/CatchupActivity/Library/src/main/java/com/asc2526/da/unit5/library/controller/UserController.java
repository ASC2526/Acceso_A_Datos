package com.asc2526.da.unit5.library.controller;

import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{code}")
    public ResponseEntity<User> getUserById(@PathVariable String code) {
        return ResponseEntity.ok(userService.getUserById(code));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User newUser = userService.createUser(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteUser(@PathVariable String code) {
        userService.deleteUser(code);
        return ResponseEntity.noContent().build();
    }
}