package com.test.assignment.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlingException {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<UserNotFoundException> userNotFound(UserNotFoundException e) {
        UserNotFoundException userNotFoundException = new UserNotFoundException(e.getMessage());
        return new ResponseEntity<>(userNotFoundException, HttpStatus.BAD_REQUEST);
    }
}
