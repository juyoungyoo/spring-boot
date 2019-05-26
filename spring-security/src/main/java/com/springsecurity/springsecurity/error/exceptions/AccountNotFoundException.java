package com.springsecurity.springsecurity.error.exceptions;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountNotFoundException extends UsernameNotFoundException {

    private String email;

    public AccountNotFoundException(String msg) {
        super(msg);
    }
}
