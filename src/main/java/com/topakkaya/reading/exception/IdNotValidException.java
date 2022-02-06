package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class IdNotValidException extends BaseBusinessException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String message = "Given id cannot be empty";

    public IdNotValidException() {
        super(message, HTTP_STATUS);
    }
}
