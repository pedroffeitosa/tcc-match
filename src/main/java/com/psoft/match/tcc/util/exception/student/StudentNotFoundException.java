package com.psoft.match.tcc.util.exception.student;

import javax.persistence.EntityNotFoundException;

public class StudentNotFoundException extends EntityNotFoundException {

    private static final String STUDENT_NOT_FOUND_MSG = "Student with id %d not found.";

    public StudentNotFoundException(Long studentId) {
        super(String.format(STUDENT_NOT_FOUND_MSG, studentId));
    }
}
