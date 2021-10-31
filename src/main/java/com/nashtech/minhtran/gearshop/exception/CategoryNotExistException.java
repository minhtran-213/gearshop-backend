package com.nashtech.minhtran.gearshop.exception;

public class CategoryNotExistException extends RuntimeException{
    public CategoryNotExistException() {
        super();
    }

    public CategoryNotExistException(String message) {
        super(message);
    }

    public CategoryNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryNotExistException(Throwable cause) {
        super(cause);
    }
}
