package com.nashtech.minhtran.gearshop.exception;

public class ProductNotExistException extends RuntimeException{
    public ProductNotExistException() {
        super();
    }

    public ProductNotExistException(String message) {
        super(message);
    }

    public ProductNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductNotExistException(Throwable cause) {
        super(cause);
    }
}
