package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class DateFormatValidationException extends BaseBusinessException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_ACCEPTABLE;
    private static final String message = "Date format is not valid! Ex : 2022-01-01";

    public DateFormatValidationException() {
        super(message, HTTP_STATUS);
    }
}
