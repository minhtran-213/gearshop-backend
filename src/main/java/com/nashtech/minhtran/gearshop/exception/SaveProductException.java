package com.nashtech.minhtran.gearshop.exception;

public class SaveProductException extends RuntimeException {
    public SaveProductException() {
    }

    public SaveProductException(String message) {
        super(message);
    }

    public SaveProductException(String message, Throwable cause) {
        super(message, cause);
    }

    public SaveProductException(Throwable cause) {
        super(cause);
    }
}
