package com.storyblocks.storyblocksservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionMessageControllerAdvice {

    @ExceptionHandler(ExceptionWithMessage.class)
    public final ResponseEntity<ExceptionMessage> exception(ExceptionWithMessage exception, WebRequest request){
        return new ResponseEntity<>(exception.getCustomMessage(), exception.getClass().getAnnotation(ResponseStatus.class).value());
    }

}
