package com.psoft.match.tcc.util.exception.user;

import com.psoft.match.tcc.util.exception.common.ForbiddenException;

public class TCCDoesNotBelongToUserException extends ForbiddenException {

    private static final String TCC_DOES_NOT_BELONG_TO_STUDENT_MSG = "The TCC with id %d does not belong to user %s";

    public TCCDoesNotBelongToUserException(String userFullName, Long tccId) {
        super(String.format(TCC_DOES_NOT_BELONG_TO_STUDENT_MSG, userFullName, tccId));
    }
}
