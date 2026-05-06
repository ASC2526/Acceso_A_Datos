package com.asc2526.da.unit5.library.exception;

public class ReservationAlreadyExistsException extends RuntimeException {
    public ReservationAlreadyExistsException(String isbn, String userId) {
        super("The book with ISBN: " + isbn + " is already reserved by the user " + userId + ".");
    }
}
