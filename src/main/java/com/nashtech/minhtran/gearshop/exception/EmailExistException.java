package com.nashtech.minhtran.gearshop.exception;

public class EmailExistException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public EmailExistException() {
        super();
    }

    public EmailExistException(String message) {
        super(message);
    }

    public EmailExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailExistException(Throwable cause) {
        super(cause);
    }
}
