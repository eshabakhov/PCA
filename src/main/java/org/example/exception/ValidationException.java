package org.example.exception;

public class ValidationException extends RuntimeException{
    private final String message;

    public ValidationException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
