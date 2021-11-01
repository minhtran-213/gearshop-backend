package com.nashtech.minhtran.gearshop.exception;

public class RetrieveProductDetailException extends RuntimeException{
    public RetrieveProductDetailException() {
        super();
    }

    public RetrieveProductDetailException(String message) {
        super(message);
    }

    public RetrieveProductDetailException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveProductDetailException(Throwable cause) {
        super(cause);
    }
}
