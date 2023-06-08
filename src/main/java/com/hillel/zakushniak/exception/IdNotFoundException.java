package com.hillel.zakushniak.exception;

public class IdNotFoundException extends RuntimeException {
    public IdNotFoundException(String  message){
        super(message);
    }

    public IdNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
