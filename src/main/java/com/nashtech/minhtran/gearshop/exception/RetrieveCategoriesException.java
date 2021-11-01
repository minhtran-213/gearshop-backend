package com.nashtech.minhtran.gearshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class RetrieveCategoriesException extends RuntimeException{
    public RetrieveCategoriesException() {
        super();
    }

    public RetrieveCategoriesException(String message) {
        super(message);
    }

    public RetrieveCategoriesException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveCategoriesException(Throwable cause) {
        super(cause);
    }
}
