package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException{

    private final HttpStatus httpStatus;
    private final String errorCode;

    public BaseException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = null;
    }

    public BaseException(String message, HttpStatus httpStatus, String errorCode) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean hasErrorCode() {
        return errorCode != null && !errorCode.isBlank();
    }
}
