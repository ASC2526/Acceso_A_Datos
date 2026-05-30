package com.asc2526.da.unit5.library.exception;

public class LendingNotFoundException extends RuntimeException {
    public LendingNotFoundException(Integer lendingId) {
        super("The lending with id " + lendingId + " not exists");
    }
}
