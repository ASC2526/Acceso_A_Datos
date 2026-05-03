package com.asc2526.da.unit5.library.exception;

public class BookAlreadyExistsException extends RuntimeException {
    public BookAlreadyExistsException(String isbn) {
        super("The book with isbn " + isbn + " already exists.");
    }
}
