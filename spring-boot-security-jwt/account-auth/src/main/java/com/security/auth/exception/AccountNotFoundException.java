package com.security.auth.exception;

import lombok.Getter;

@Getter
public class AccountNotFoundException extends RuntimeException {

    private long id;
    private String email;

    public AccountNotFoundException(String email) {
        this.email = email;
    }

    public AccountNotFoundException(long id) {
        this.id = id;
    }
}