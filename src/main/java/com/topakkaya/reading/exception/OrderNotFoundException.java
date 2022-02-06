package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends BaseBusinessException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String message = "Order not found!";

    public OrderNotFoundException() {
        super(message, HTTP_STATUS);
    }
}
