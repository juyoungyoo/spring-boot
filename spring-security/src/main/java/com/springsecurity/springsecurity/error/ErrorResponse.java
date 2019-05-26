package com.springsecurity.springsecurity.error;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private HttpStatus status;
    private ErrorCode errorCode;
    private String message;

    public static ErrorResponse of(String message, ErrorCode errorCode, HttpStatus status){
        return new ErrorResponse(status, errorCode, message);
    }

    private ErrorResponse(HttpStatus status,
                         ErrorCode errorCode,
                         String message) {
        this.status = status;
        this.errorCode = errorCode;
        this.message = message;
    }
}
