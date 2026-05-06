package com.asc2526.da.unit5.library.exception;

public class LendingNotActiveException extends RuntimeException {
    public LendingNotActiveException(String isbn, String userId) {
        super("There is no active lending for the book with ISBN: " + isbn + " for the user: " + userId);
    }
}
