package com.nashtech.minhtran.gearshop.exception;

public class ManufacturerNotExistException extends RuntimeException{
    public ManufacturerNotExistException() {
    }

    public ManufacturerNotExistException(String message) {
        super(message);
    }

    public ManufacturerNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public ManufacturerNotExistException(Throwable cause) {
        super(cause);
    }
}
