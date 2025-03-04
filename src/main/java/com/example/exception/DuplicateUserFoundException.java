package com.example.exception;

public class DuplicateUserFoundException extends RuntimeException  {
    /**
     * Its a Custom Exception to be thrown if an account already exists with the given username
     * @param message
     */
    public DuplicateUserFoundException(String message){
        super(message);
    }
}
