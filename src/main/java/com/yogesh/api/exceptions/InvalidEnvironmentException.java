package com.yogesh.api.exceptions;

public class InvalidEnvironmentException extends RuntimeException{

    public InvalidEnvironmentException(String message){
        super(message);
    }
}
