package com.csci318.microservice.analytics.Exceptions.ServiceExceptionHandler;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    private final ErrorTypes errorType;

    public ServiceException(String message, Throwable cause, ErrorTypes errorType) {
        super(message, cause);
        this.errorType = errorType;
    }
}
