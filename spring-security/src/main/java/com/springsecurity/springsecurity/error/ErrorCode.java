package com.springsecurity.springsecurity.error;

public enum ErrorCode {

    AUTHENTICATION(10),
    JWT_TOKEN_EXPIRED(11);

    int errorCode;
    ErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }
}
