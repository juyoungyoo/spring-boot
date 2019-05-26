package com.springsecurity.springsecurity.error.exceptions;

import org.springframework.security.core.AuthenticationException;

public class JwtExpiredTokenException extends AuthenticationException {

    public JwtExpiredTokenException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtExpiredTokenException(String msg) {
        super(msg);
    }
}