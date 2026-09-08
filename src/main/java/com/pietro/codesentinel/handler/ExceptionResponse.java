package com.pietro.codesentinel.handler;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public class ExceptionResponse {
    private int status;
    private String error;
    private String message;
    private OffsetDateTime timeStamp;

    public ExceptionResponse(int status, String error, String message, OffsetDateTime timeStamp) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public OffsetDateTime getTimeStamp() {
        return timeStamp;
    }
}
