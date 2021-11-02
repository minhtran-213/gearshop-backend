package com.nashtech.minhtran.gearshop.exception;

public class RetrieveSingleCategoryException extends RuntimeException {
    public RetrieveSingleCategoryException() {
        super();
    }

    public RetrieveSingleCategoryException(String message) {
        super(message);
    }

    public RetrieveSingleCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveSingleCategoryException(Throwable cause) {
        super(cause);
    }
}
