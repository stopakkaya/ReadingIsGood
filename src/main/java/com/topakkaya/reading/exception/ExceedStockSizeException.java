package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class ExceedStockSizeException extends BaseBusinessException {

    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;
    private static final String message = "Order Amount cannot be greater than stock size";
    public ExceedStockSizeException() {
        super(message, HTTP_STATUS);
    }
}
