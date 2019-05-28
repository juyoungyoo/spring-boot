package com.juyoung.res.web.exception;

import lombok.Getter;

@Getter
public class EmailDuplicationException extends RuntimeException {

    private String field;
    private String email;

    public EmailDuplicationException(String email) {
        this.field = "email";
        this.email = email;
    }
}