package com.challenge.w2m.superheros.exception;

public class ExceptionDTO {

    private String errorStatus;
    private String message;

    public ExceptionDTO(String errorStatus, String message) {
        this.errorStatus = errorStatus;
        this.message = message;
    }

    public String getErrorStatus() {
        return errorStatus;
    }

    public String getMessage() {
        return message;
    }
}
