package com.asc2526.da.unit5.library.exception;

public class BookNotAvailableException extends RuntimeException {
    public BookNotAvailableException(String isbn) {
        super("Book with isbn: " + isbn + " is not available");
    }
}
