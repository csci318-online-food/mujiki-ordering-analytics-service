package com.csci318.microservice.analytics.Constants.HttpStatus;

public class ErrorDefine {
    // Http status with basic responses via Status Code
    public static final Integer SUCCESS = 200; // Success
    public static final Integer INVALID_ARGUMENT = 400; // Bad request, e.g., invalid parameters
    public static final Integer UNAUTHORIZED = 401; // Username or password incorrect
    public static final Integer FORBIDDEN = 403; // No permission
    public static final Integer NOT_FOUND = 404; // Not found
    public static final Integer INTERNAL_SERVER_ERROR = 500;
}
