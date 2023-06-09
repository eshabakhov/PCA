package org.example.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHander extends ResponseEntityExceptionHandler {

    /**
     * Обработчик любых необработанных исключений - записывает ошибку в логи и отдаёт ответ об ошибке на сервере
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(
            final Exception e,
            final WebRequest request
    ) {
        StringBuilder stackTrace = new StringBuilder("Error: " + e.getMessage());
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            stackTrace.append("\n").append(stackTraceElement.toString());
        }

        e.printStackTrace();

        String responseMessage = "Произошла ошибка на сервере";

        return handleExceptionInternal(
                e,
                responseMessage,
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    /**
     * При validationException возвращает 400 клиенту и сообщение
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            final ValidationException e,
            final WebRequest request
    ) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}