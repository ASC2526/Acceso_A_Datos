package com.asc2526.da.unit5.vtschool_rest_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleStudentNotFound(StudentNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(CourseNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleCourseNotFound(CourseNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EnrollmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleEnrollmentNotFound(EnrollmentNotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(SubjectNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleSubjectNotFound(SubjectNotFoundException ex) {
        return ex.getMessage();
    }
    @ExceptionHandler(StudentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleStudentAlreadyExists(StudentAlreadyExistsException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(EnrollmentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleEnrollmentAlreadyExists(EnrollmentAlreadyExistsException ex) {
        return ex.getMessage();
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleValidationErrors(MethodArgumentNotValidException ex) {
        return ex.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleIllegalState(IllegalStateException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(IllegalArgumentException ex) {
        return ex.getMessage();
    }

}
