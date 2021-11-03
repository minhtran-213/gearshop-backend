package com.nashtech.minhtran.gearshop.exception;

public class RetrieveAddressException extends RuntimeException {
    public RetrieveAddressException() {
    }

    public RetrieveAddressException(String message) {
        super(message);
    }

    public RetrieveAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public RetrieveAddressException(Throwable cause) {
        super(cause);
    }
}
