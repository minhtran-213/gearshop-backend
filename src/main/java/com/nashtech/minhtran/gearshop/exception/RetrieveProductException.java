package com.nashtech.minhtran.gearshop.exception;

public class RetrieveProductException extends RuntimeException{
    public RetrieveProductException() {
        super();
    }

    public RetrieveProductException(String message) {
        super(message);
    }

    public RetrieveProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveProductException(Throwable cause) {
        super(cause);
    }
}
