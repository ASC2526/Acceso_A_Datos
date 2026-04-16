package com.asc2526.da.unit5.library.exception;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String isbn) {
        super("Book with isbn: " + isbn + " not found");
    }
}
