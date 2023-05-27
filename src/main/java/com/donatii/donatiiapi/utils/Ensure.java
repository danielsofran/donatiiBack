package com.donatii.donatiiapi.utils;

import com.donatii.donatiiapi.service.exceptions.EmptyObjectException;

public class Ensure {
    public static void NotNull(Object object)
    {
        if(object == null)
            throw new NullPointerException();
    }

    public static void NotNullOrEmpty(Object object) throws EmptyObjectException {
        if(object == null)
            throw new NullPointerException();
        else
            if(object == "")
                throw new EmptyObjectException(String.format("%s is empty", object.getClass()));
    }
}
