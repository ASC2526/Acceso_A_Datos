package com.asc2526.da.unit5.vtschool_rest_api.exception;

public class SubjectNotFoundException extends RuntimeException {

    public SubjectNotFoundException(Integer id) {
        super("Subject with id " + id + " not found");
    }
}