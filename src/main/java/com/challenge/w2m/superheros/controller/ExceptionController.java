package com.challenge.w2m.superheros.controller;

import com.challenge.w2m.superheros.exception.ApiException;
import com.challenge.w2m.superheros.exception.ExceptionDTO;
import com.challenge.w2m.superheros.exception.SuperheroException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<ExceptionDTO> apiExceptionHandler(ApiException ex) {
        return new ResponseEntity<>(
                new ExceptionDTO(HttpStatus.INTERNAL_SERVER_ERROR.toString(), ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = SuperheroException.class)
    public ResponseEntity<ExceptionDTO> superheroExceptionHandler(SuperheroException ex) {
        StringBuilder stringBuilder = new StringBuilder();
        return new ResponseEntity<>(
                new ExceptionDTO(ex.getStatus().toString(), stringBuilder.append("[").append(ex.getField())
                        .append("] - ").append(ex.getMessage()).toString()),
                ex.getStatus());
    }
}
