package com.asc2526.da.unit5.library.exception;

public class BookAlreadyReservedException extends RuntimeException {
    public BookAlreadyReservedException(String isbn) {
        super("Book with ISBN: " + isbn + " is already reserved by another user");
    }
}
