package com.storyblocks.storyblocksservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionMessageControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionMessage> exception(ResourceNotFoundException exception, WebRequest request){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalStateException.class)
    public final ResponseEntity<ExceptionMessage> illegalStateException(IllegalStateException exception, WebRequest request){
        return new ResponseEntity<>(new ExceptionMessage(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
