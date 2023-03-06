package com.psoft.match.tcc.util.exception.user;

import com.psoft.match.tcc.util.exception.common.ConflictException;

public class UserAlreadyInterestedInStudyAreaException extends ConflictException {

    private static final String USER_ALREADY_INTERESTED_IN_STUDY_AREA_MSG = "User %s is already interested in study area with description %s";

    public UserAlreadyInterestedInStudyAreaException(String professorName, String studyAreaDescription) {
        super(String.format(USER_ALREADY_INTERESTED_IN_STUDY_AREA_MSG, professorName, studyAreaDescription));
    }
}