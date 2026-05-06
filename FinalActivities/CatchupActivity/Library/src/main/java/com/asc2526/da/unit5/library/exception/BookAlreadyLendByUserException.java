package com.asc2526.da.unit5.library.exception;

public class BookAlreadyLendByUserException extends RuntimeException {
    public BookAlreadyLendByUserException(String userId, String isbn) {
        super("The user " + userId + " already has the book with ISBN " + isbn + " lend.");
    }
}
