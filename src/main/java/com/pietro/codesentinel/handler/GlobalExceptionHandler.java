package com.pietro.codesentinel.handler;

import com.pietro.codesentinel.model.LogType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionResponse> argumentException(MethodArgumentTypeMismatchException ex){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        "Invalid value '%s' for parameter %s. Accepted values: %s".formatted(ex.getValue(), ex.getName(),
                                ex.getRequiredType() == LogType.class
                                        ? Arrays.toString(LogType.values())
                                        : "Expected Format: " + "yyyy-MM-dd"),
                        OffsetDateTime.now()));
    }
}
