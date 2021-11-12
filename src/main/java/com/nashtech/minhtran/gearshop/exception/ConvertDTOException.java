package com.nashtech.minhtran.gearshop.exception;

public class ConvertDTOException extends RuntimeException{
    public ConvertDTOException() {
        super();
    }

    public ConvertDTOException(String message) {
        super(message);
    }

    public ConvertDTOException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConvertDTOException(Throwable cause) {
        super(cause);
    }
}
