package com.storyblocks.storyblocksservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public abstract class ExceptionWithMessage extends RuntimeException {

    private final String message;

    private final String description;

    public ExceptionWithMessage(String message, String description) {
        this.message = message;
        this.description = description;
    }

    public ExceptionMessage getCustomMessage(){
        return new ExceptionMessage(message, description);
    }

}
