package com.psoft.match.tcc.util.exception.auth;

import com.psoft.match.tcc.util.exception.common.UnauthorizedException;

public class InvalidCredentialsException extends UnauthorizedException {

    public InvalidCredentialsException() {
        super("Wrong username or password");
    }
}
