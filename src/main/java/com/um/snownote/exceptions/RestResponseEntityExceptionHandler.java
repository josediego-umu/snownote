package com.um.snownote.exceptions;

import com.um.snownote.services.implementation.Analyzer;
import io.jsonwebtoken.JwtException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.logging.Logger;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Analyzer.class);

    @ExceptionHandler(value = {JwtException.class})
    public ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        logger.error("JWT token is invalid or expired", ex);
        String bodyOfResponse = "JWT token is invalid or expired";
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {LoginException.class})
    public ResponseEntity<Object> handleLogin(RuntimeException ex, WebRequest request) {

        logger.error("Invalid username or password", ex);
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {Exception.class})
    public ResponseEntity<Object> handleInternalServerError(RuntimeException ex, WebRequest request) {
        logger.error("Internal server error", ex);
        String bodyOfResponse = ex.getMessage();
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
