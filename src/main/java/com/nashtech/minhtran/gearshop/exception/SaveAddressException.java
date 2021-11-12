package com.nashtech.minhtran.gearshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class SaveAddressException extends RuntimeException{
    public SaveAddressException() {
        super();
    }

    public SaveAddressException(String message) {
        super(message);
    }

    public SaveAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveAddressException(Throwable cause) {
        super(cause);
    }
}
