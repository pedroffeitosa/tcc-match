package com.psoft.match.tcc.util.exception.user;

import com.psoft.match.tcc.util.exception.common.ConflictException;

public class UserAlreadyExistsException extends ConflictException {

    private static final String USER_ALREADY_EXISTS_MSG = "User with email %s already exists.";

    public UserAlreadyExistsException(String email) {
        super(String.format(USER_ALREADY_EXISTS_MSG, email));
    }
}
