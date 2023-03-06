package com.psoft.match.tcc.util.exception.professor;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class UnavailableProfessorException extends BadRequestException {

    private static final String UNAVAILABLE_PROFESSOR_EXCEPTION_MSG = "The professor with id %d has already fulfilled his quota.";

    public UnavailableProfessorException(Long professorId) {
        super(String.format(UNAVAILABLE_PROFESSOR_EXCEPTION_MSG, professorId));
    }
}
