package com.csci318.microservice.analytics.Exceptions.ControllerExceptionHandler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseControllerException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public BaseControllerException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
