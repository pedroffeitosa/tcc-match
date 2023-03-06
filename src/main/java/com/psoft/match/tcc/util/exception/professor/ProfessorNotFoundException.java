package com.psoft.match.tcc.util.exception.professor;

import javax.persistence.EntityNotFoundException;

public class ProfessorNotFoundException extends EntityNotFoundException {

    private static final String PROFESSOR_NOT_FOUND_MSG = "Professor with id %d not found.";

    public ProfessorNotFoundException(Long id) {
        super(String.format(PROFESSOR_NOT_FOUND_MSG, id));
    }
}
