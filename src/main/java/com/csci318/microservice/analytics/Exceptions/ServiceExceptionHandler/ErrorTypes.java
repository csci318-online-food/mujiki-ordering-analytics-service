package com.csci318.microservice.analytics.Exceptions.ServiceExceptionHandler;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorTypes {

    // General Errors for Service Layer
    UNEXPECTED_ERROR("UNEXPECTED_ERROR", "An unexpected error occurred!"),

    ;

    private final String code;
    private final String message;
}
