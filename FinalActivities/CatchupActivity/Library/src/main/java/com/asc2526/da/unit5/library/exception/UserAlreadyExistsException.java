package com.asc2526.da.unit5.library.exception;

public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String code) {
        super("The user with id: " + code + " already exists.");
    }
}
