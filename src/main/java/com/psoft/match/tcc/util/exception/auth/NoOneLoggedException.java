package com.psoft.match.tcc.util.exception.auth;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class NoOneLoggedException extends BadRequestException {

    public NoOneLoggedException() {
        super("There is no user logged.");
    }
}
