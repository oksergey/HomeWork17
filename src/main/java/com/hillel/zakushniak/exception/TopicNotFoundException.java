package com.hillel.zakushniak.exception;

import java.sql.SQLException;

public class TopicNotFoundException extends RuntimeException {
    public TopicNotFoundException(SQLException message){
        super(message);
    }

    public TopicNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
