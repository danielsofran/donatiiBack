package com.donatii.donatiiapi.service.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class NotFoundException extends MyException {
    public NotFoundException(String message) {
        super(message);
    }
}
