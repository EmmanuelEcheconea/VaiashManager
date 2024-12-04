package com.vaiashmanager.db.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CustomExceptionHandler extends RuntimeException{


    private String message;
    private int status;

    public CustomExceptionHandler(String message, int status) {
        super(String.format("Error: " + message + ", Status: " +
                status));
        this.message = message;
        this.status = status;
    }

}
