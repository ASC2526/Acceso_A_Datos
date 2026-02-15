package com.asc2526.da.unit5.vtschool_rest_api.exception;

public class EnrollmentNotFoundException extends RuntimeException {

    public EnrollmentNotFoundException(Integer id) {
        super("Enrollment with id " + id + " not found");
    }
}
