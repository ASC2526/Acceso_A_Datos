package com.asc2526.da.unit5.library.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("User with id " + userId + " not found");
    }
}
