package com.apper.theblogservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> handleInvalidEmail(MethodArgumentNotValidException e) {
        CustomErrorResponse customMessage = new CustomErrorResponse();
        customMessage.setMessage(e.getFieldError().getDefaultMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
    }
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<Object> handleDuplicateEmail(NullPointerException e) {
        CustomErrorResponse customMessage = new CustomErrorResponse();
        customMessage.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(customMessage);
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public ResponseEntity<Object> handleGetNonexistentAccount(NoSuchElementException e) {
        CustomErrorResponse customMessage = new CustomErrorResponse();
        customMessage.setMessage(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customMessage);
    }

}

