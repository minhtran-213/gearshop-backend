package com.nashtech.minhtran.gearshop.exception;

public class EmptyNameCategoryException extends RuntimeException{
    public EmptyNameCategoryException() {
        super();
    }

    public EmptyNameCategoryException(String message) {
        super(message);
    }

    public EmptyNameCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNameCategoryException(Throwable cause) {
        super(cause);
    }
}
