package com.asc2526.da.unit5.api;

public class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(String message) {
        super(message);
    }
}