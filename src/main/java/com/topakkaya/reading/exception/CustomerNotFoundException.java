package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class CustomerNotFoundException extends BaseBusinessException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String message = "Customer not found!";

    public CustomerNotFoundException() {
        super(message, HTTP_STATUS);
    }
}
