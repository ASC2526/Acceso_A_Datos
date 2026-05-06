package com.asc2526.da.unit5.library.exception;

public class CategoryAlreadyExistsException extends RuntimeException {
    public CategoryAlreadyExistsException(String code) {
        super("Category with code " + code + " already exists.");
    }
}
