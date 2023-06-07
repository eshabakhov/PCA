package org.example.exception;
import lombok.Data;

@Data
public class ValidationException extends RuntimeException{
    private final String message;

    public ValidationException(String message) {
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

}
