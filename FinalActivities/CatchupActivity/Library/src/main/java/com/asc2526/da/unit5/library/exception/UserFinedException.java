package com.asc2526.da.unit5.library.exception;

public class UserFinedException extends RuntimeException {
    public UserFinedException(String userId) {
        super("User with id: " + userId + " is fined and cannot borrow books");
    }
}
