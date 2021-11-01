package com.nashtech.minhtran.gearshop.exception;

public class RetrieveCategoriesByNameException extends RuntimeException{
    public RetrieveCategoriesByNameException() {
        super();
    }

    public RetrieveCategoriesByNameException(String message) {
        super(message);
    }

    public RetrieveCategoriesByNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveCategoriesByNameException(Throwable cause) {
        super(cause);
    }
}
