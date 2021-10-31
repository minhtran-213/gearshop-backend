package com.nashtech.minhtran.gearshop.exception;

public class ProductDetailNotExistException extends RuntimeException{
    public ProductDetailNotExistException() {
        super();
    }

    public ProductDetailNotExistException(String message) {
        super(message);
    }

    public ProductDetailNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDetailNotExistException(Throwable cause) {
        super(cause);
    }
}
