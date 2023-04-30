package com.donatii.donatiiapi.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class MyException extends Exception {
    protected String message;
    protected Exception cause;

    public MyException(String message) {
        this.message = message;
    }
}