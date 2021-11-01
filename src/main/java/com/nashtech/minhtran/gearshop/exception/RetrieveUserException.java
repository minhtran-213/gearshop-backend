package com.nashtech.minhtran.gearshop.exception;

public class RetrieveUserException extends RuntimeException{
    public RetrieveUserException() {
        super();
    }

    public RetrieveUserException(String message) {
        super(message);
    }

    public RetrieveUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveUserException(Throwable cause) {
        super(cause);
    }
}
