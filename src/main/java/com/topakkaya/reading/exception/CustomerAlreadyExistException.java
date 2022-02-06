package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class CustomerAlreadyExistException extends RetailException{
    private static final HttpStatus HTTP_STATUS = HttpStatus.ALREADY_REPORTED;
    private static final String message = "Email already exist!";

    public CustomerAlreadyExistException() {
        super(message, HTTP_STATUS);
    }
}
