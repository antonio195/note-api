package com.antoniocostadossantos.exceptions;

public class InvalidUserCredentialsException extends RuntimeException{

    public InvalidUserCredentialsException(String message){
        super(message);
    }
}
