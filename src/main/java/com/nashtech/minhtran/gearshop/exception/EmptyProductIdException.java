package com.nashtech.minhtran.gearshop.exception;

public class EmptyProductIdException extends RuntimeException{
    public EmptyProductIdException() {
        super();
    }

    public EmptyProductIdException(String message) {
        super(message);
    }

    public EmptyProductIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyProductIdException(Throwable cause) {
        super(cause);
    }
}
