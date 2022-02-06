package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public abstract class RetailException extends RuntimeException {
    private final HttpStatus httpStatus;

    public RetailException(String message, HttpStatus httpStatus){
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
