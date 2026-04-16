package com.asc2526.da.unit5.library.exception;

public class MaxBooksExceededException extends RuntimeException {
    public MaxBooksExceededException(String userId) {
        super("User " + userId + " already has 3 active lendings");
    }
}
