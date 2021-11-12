package com.nashtech.minhtran.gearshop.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class RoleExistedException extends RuntimeException{
    public RoleExistedException() {
        super();
    }

    public RoleExistedException(String message) {
        super(message);
    }

    public RoleExistedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RoleExistedException(Throwable cause) {
        super(cause);
    }
}
