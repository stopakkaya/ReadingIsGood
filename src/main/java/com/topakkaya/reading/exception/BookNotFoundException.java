package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class BookNotFoundException extends BaseBusinessException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String message = "Book not found!";

    public BookNotFoundException() {
        super(message, HTTP_STATUS);
    }
}
