package com.asc2526.da.unit5.library.service;

import com.asc2526.da.unit5.library.exception.UserAlreadyExistsException;
import com.asc2526.da.unit5.library.exception.UserNotFoundException;
import com.asc2526.da.unit5.library.model.User;
import com.asc2526.da.unit5.library.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String code) {
        if (code == null)
            throw new IllegalArgumentException("The user code cannot be null");

        return userRepository.findById(code)
                .orElseThrow(() -> new UserNotFoundException(code));
    }

    public User createUser(User user) {
        if (user.getCode() == null || user.getCode().isBlank())
            throw new IllegalArgumentException("User code is required");

        if (userRepository.existsById(user.getCode()))
            throw new UserAlreadyExistsException(user.getCode());

        return userRepository.save(user);
    }

    public void deleteUser(String code) {
        if (code == null || code.isBlank())
            throw new IllegalArgumentException("The user code cannot be null");

        User user = userRepository.findById(code)
                .orElseThrow(() -> new UserNotFoundException(code));

        userRepository.delete(user);
    }

    @Transactional
    public boolean updateUser(String code, String name, String surname) {
        if(code == null || code.isBlank())
            throw new IllegalArgumentException("The user code cannot be null");

        User existingUser = userRepository.findById(code)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        boolean surnameChanged = !existingUser.getSurname().equalsIgnoreCase(surname);

        existingUser.setName(name);
        existingUser.setSurname(surname);

        userRepository.save(existingUser);
        return surnameChanged;
    }
}