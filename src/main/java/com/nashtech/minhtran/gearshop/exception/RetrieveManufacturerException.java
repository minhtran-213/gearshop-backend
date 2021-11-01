package com.nashtech.minhtran.gearshop.exception;

public class RetrieveManufacturerException extends RuntimeException{
    public RetrieveManufacturerException() {
        super();
    }

    public RetrieveManufacturerException(String message) {
        super(message);
    }

    public RetrieveManufacturerException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveManufacturerException(Throwable cause) {
        super(cause);
    }
}
