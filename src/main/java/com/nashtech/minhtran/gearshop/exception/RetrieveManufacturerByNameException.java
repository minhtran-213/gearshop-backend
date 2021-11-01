package com.nashtech.minhtran.gearshop.exception;

public class RetrieveManufacturerByNameException extends RuntimeException{
    public RetrieveManufacturerByNameException() {
        super();
    }

    public RetrieveManufacturerByNameException(String message) {
        super(message);
    }

    public RetrieveManufacturerByNameException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveManufacturerByNameException(Throwable cause) {
        super(cause);
    }
}
