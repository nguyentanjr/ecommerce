package com.example.ecommerce.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidInputException extends RuntimeException{

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String parameterName, Object value, String reason) {
        super(String.format("Invalid value '%s' for parameter '%s': %s",
                value, parameterName, reason));
    }
}
