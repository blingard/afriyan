package org.ligot.afriyan.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public Map<String, String> errorHandler(Exception ex){
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        Map<String, String> map = new HashMap<>();
        map.put("error", ex.getMessage());
        return map;
    }

    @ExceptionHandler(IOException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public void handleIOException(IOException ex) {
        // Souvent une ClientAbortException si le client coupe la connexion
        // On logue en WARN car c'est souvent externe (réseau, client qui part)
        log.warn("I/O error (likely client disconnection): {}", ex.getMessage());
    }
}

