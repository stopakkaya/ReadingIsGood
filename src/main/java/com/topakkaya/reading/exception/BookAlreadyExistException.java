package com.topakkaya.reading.exception;

import org.springframework.http.HttpStatus;

public class BookAlreadyExistException extends BaseBusinessException {
    private static final HttpStatus HTTP_STATUS = HttpStatus.ALREADY_REPORTED;
    private static final String message = "Book already exist for this author and publisher";

    public BookAlreadyExistException() {
        super(message, HTTP_STATUS);
    }
}
