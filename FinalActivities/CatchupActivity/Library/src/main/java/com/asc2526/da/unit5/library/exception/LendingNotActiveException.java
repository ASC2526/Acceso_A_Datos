package com.asc2526.da.unit5.library.exception;

public class LendingNotActiveException extends RuntimeException {
    public LendingNotActiveException(String isbn, String userId) {
        super("No hay préstamo activo del libro con isbn: " + isbn + " para el usuario: " + userId);
    }
}
