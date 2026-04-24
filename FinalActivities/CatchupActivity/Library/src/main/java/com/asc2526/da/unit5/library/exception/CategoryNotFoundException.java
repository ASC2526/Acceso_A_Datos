package com.asc2526.da.unit5.library.exception;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(String code) {
        super("Category not found with code: " + code);
    }
}