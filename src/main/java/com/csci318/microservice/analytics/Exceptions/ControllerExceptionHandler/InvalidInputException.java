package com.csci318.microservice.analytics.Exceptions.ControllerExceptionHandler;

import org.springframework.http.HttpStatus;

public class InvalidInputException extends BaseControllerException {
    public InvalidInputException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
