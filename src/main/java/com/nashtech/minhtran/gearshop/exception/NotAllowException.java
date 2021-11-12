package com.nashtech.minhtran.gearshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class NotAllowException extends RuntimeException{
    public NotAllowException() {
        super();
    }

    public NotAllowException(String message) {
        super(message);
    }

    public NotAllowException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAllowException(Throwable cause) {
        super(cause);
    }
}
