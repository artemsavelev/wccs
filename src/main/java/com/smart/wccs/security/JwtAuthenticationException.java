package com.smart.wccs.security;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    @Getter
    private HttpStatus httpStatus;

    public JwtAuthenticationException(String msg) {
        super(msg);
    }

//    public JwtAuthenticationException(String msg, HttpStatus httpStatus) {
//        super(msg);
//        this.httpStatus = httpStatus;
//    }

    public JwtAuthenticationException(String msg, Throwable t, HttpStatus httpStatus) {
        super(msg, t);
        this.httpStatus = httpStatus;
    }
}
