package com.example.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends RuntimeException{

    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User with %s already exists: %s", field, value));
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
