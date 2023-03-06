package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class TCCRegisteredByStudentException extends BadRequestException {

    private static final String TCC_REGISTERED_BY_STUDENT_MSG = "TCC %d have a student as a author.";

    public TCCRegisteredByStudentException(Long tccId) {
        super(String.format(TCC_REGISTERED_BY_STUDENT_MSG, tccId));
    }
}
