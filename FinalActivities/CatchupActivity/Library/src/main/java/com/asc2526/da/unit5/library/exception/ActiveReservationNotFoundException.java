package com.asc2526.da.unit5.library.exception;

public class ActiveReservationNotFoundException extends RuntimeException{
    public ActiveReservationNotFoundException(String isbn, String userId) {
        super("There is no active reservation for the book: " + isbn + " and the user: " + userId);
    }
}
