package com.asc2526.da.unit5.vtschool_rest_api.exception;

public class CourseNotFoundException extends RuntimeException {
    public CourseNotFoundException(Integer id) {
        super("Course with id " + id + " not found");
    }
}
