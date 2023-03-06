package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class TCCRegisteredByProfessorException extends BadRequestException {

    private static final String TCC_REGISTERED_BY_PROFESSOR_MSG = "TCC %d have a professor as a author.";

    public TCCRegisteredByProfessorException(Long tccId) {
        super(String.format(TCC_REGISTERED_BY_PROFESSOR_MSG, tccId));
    }
}
