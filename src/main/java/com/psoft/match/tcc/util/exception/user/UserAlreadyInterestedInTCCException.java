package com.psoft.match.tcc.util.exception.user;

import com.psoft.match.tcc.util.exception.common.ConflictException;

public class UserAlreadyInterestedInTCCException extends ConflictException {

    private static final String USER_ALREADY_INTERESTED_IN_TCC_MSG = "User %s is already interested in TCC '%s'";

    public UserAlreadyInterestedInTCCException(String userFullName, String tccTitle) {
        super(String.format(USER_ALREADY_INTERESTED_IN_TCC_MSG, userFullName, tccTitle));
    }
}