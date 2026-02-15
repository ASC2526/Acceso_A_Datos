package com.asc2526.da.unit5.vtschool_rest_api.exception;

public class StudentAlreadyExistsException extends RuntimeException {
    public StudentAlreadyExistsException(String idcard) {
        super("Student with idcard " + idcard + " already exists");
    }
}
