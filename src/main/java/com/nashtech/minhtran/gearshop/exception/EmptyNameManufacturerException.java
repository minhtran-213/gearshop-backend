package com.nashtech.minhtran.gearshop.exception;

public class EmptyNameManufacturerException extends RuntimeException{
    public EmptyNameManufacturerException() {
        super();
    }

    public EmptyNameManufacturerException(String message) {
        super(message);
    }

    public EmptyNameManufacturerException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmptyNameManufacturerException(Throwable cause) {
        super(cause);
    }
}
