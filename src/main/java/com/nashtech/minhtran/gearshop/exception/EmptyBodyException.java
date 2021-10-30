package com.nashtech.minhtran.gearshop.exception;

public class EmptyBodyException extends RuntimeException{
    public EmptyBodyException() {
        super();
    }

    public EmptyBodyException(String message) {
        super(message);
    }

    public EmptyBodyException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyBodyException(Throwable cause) {
        super(cause);
    }
}
