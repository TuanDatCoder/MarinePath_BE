package com.example.marinepath.exception;


public class AlreadyExistedException extends RuntimeException{
    public AlreadyExistedException(String message) {
        super(message);
    }

}
