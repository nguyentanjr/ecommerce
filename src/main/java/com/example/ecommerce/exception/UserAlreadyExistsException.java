package com.example.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserAlreadyExistsException extends BaseException{

    public UserAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT, "USER_ALREADY_EXISTS");
    }

    public UserAlreadyExistsException(String field, String value) {
        super(String.format("User with %s '%s' already exists", field, value),
                HttpStatus.CONFLICT, "USER_ALREADY_EXISTS");
    }
}
