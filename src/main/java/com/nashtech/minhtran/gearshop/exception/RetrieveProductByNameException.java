package com.nashtech.minhtran.gearshop.exception;

public class RetrieveProductByNameException extends RuntimeException{
    public RetrieveProductByNameException() {
        super();
    }

    public RetrieveProductByNameException(String message) {
        super(message);
    }

    public RetrieveProductByNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveProductByNameException(Throwable cause) {
        super(cause);
    }
}
