package com.nashtech.minhtran.gearshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SaveUserException extends RuntimeException{
    public SaveUserException() {
        super();
    }

    public SaveUserException(String message) {
        super(message);
    }

    public SaveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveUserException(Throwable cause) {
        super(cause);
    }
}
