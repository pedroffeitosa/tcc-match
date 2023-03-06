package com.psoft.match.tcc.util.exception.auth;

import com.psoft.match.tcc.util.exception.common.UnauthorizedException;

public class ExpiredTokenException extends UnauthorizedException {

    private static final String EXPIRED_TOKEN_EXCEPTION_MSG = "Expired token. Please log in again.";

    public ExpiredTokenException() {
        super(EXPIRED_TOKEN_EXCEPTION_MSG);
    }
}
