package com.donatii.donatiiapi.service.exceptions;

public class EmptyObjectException extends MyException {

    public EmptyObjectException(String message, Exception cause) {
        super(message, cause);
    }

    public EmptyObjectException(String message) {
        super(message);
    }
}
