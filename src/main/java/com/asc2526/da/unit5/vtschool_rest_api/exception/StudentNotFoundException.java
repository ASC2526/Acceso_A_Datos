package com.asc2526.da.unit5.vtschool_rest_api.exception;

public class StudentNotFoundException extends RuntimeException {

    public StudentNotFoundException(String idcard) {
        super("Student with idcard " + idcard + " not found");
    }
}
