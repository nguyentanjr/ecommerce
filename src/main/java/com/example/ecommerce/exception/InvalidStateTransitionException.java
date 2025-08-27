package com.example.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidStateTransitionException extends BaseException  {

    public InvalidStateTransitionException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "INVALID_STATE_TRANSITION");
    }

    public InvalidStateTransitionException(String entityType, String currentState, String attemptedAction) {
        super(String.format("Cannot perform '%s' on %s in current state: %s",
                        attemptedAction, entityType, currentState),
                HttpStatus.BAD_REQUEST, "INVALID_STATE_TRANSITION");
    }

}
