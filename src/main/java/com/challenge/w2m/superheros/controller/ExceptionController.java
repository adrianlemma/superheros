package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.exception.SuperheroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Object> apiExceptionHandler(ApiException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = SuperheroException.class)
    public ResponseEntity<Object> superheroExceptionHandler(SuperheroException ex) {
        return new ResponseEntity<>("[" + ex.getField() + "] - " + ex.getMessage(), ex.getStatus());
    }
}
