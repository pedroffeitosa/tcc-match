package com.psoft.match.tcc.util.exception.common;

public class UnauthorizedException extends RuntimeException {

    private static final String UNAUTHORIZED_EXCEPTION_MSG = "You are not allowed to do that!";

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException() {
        super(UNAUTHORIZED_EXCEPTION_MSG);
    }
}
