package com.challenge.w2m.superheros.exception;

import org.springframework.http.HttpStatus;

public class SuperheroException extends RuntimeException {

    private final String field;

    private final HttpStatus status;

    public SuperheroException(String field, String message, HttpStatus status) {
        super(message);
        this.field = field;
        this.status = status;
    }

    public String getField() {
        return field;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
