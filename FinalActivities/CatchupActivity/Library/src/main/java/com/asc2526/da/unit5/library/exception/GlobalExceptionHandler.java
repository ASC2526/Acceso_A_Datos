package com.asc2526.da.unit5.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 not found

    @ExceptionHandler({
            BookNotFoundException.class,
            UserNotFoundException.class,
            CategoryNotFoundException.class,
            LendingNotActiveException.class
    })
    public ResponseEntity<ApiError> handleNotFound(Exception ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    // 409 conflict

    @ExceptionHandler({
            MaxBooksExceededException.class,
            BookNotAvailableException.class,
            BookAlreadyReservedException.class,
            UserFinedException.class,
            ReservationAlreadyExistsException.class,
            BookAlreadyLendByUserException.class,
            UserAlreadyExistsException.class,
            BookAlreadyExistsException.class,
            CategoryAlreadyExistsException.class
    })
    public ResponseEntity<ApiError> handleConflict(Exception ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // 400 bad request

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // JSON error

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiError> handleInvalidJson(HttpMessageNotReadableException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "JSON format error: Check braces, commas, and object structure.");
    }


    // Bean validation

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationErrors(MethodArgumentNotValidException ex) {
        String errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed: " + errors);
    }

    // Type mismatch

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiError> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Parameter type mismatch: " + ex.getName());
    }

    // 500 generic error

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneric(Exception ex) {
        ex.printStackTrace();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error: " + ex.getMessage());
    }

    private ResponseEntity<ApiError> buildResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ApiError(status.value(), message));
    }

}