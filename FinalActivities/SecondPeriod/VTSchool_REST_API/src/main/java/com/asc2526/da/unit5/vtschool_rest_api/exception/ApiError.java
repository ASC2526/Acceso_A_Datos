package com.asc2526.da.unit5.vtschool_rest_api.exception;

import java.time.LocalDateTime;

public class ApiError {

    private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ApiError() {
    }

    public ApiError(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
