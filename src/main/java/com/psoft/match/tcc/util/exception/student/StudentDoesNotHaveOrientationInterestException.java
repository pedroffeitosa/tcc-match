package com.psoft.match.tcc.util.exception.student;

import com.psoft.match.tcc.util.exception.common.ConflictException;

public class StudentDoesNotHaveOrientationInterestException extends ConflictException {
    private static final String STUDENT_DOES_NOT_HAVE_ORIENTATION_INTEREST = "Student %s does not have interest on TCC '%s'";

    public StudentDoesNotHaveOrientationInterestException(String studentName, String tccTitle) {
        super(String.format(STUDENT_DOES_NOT_HAVE_ORIENTATION_INTEREST, studentName, tccTitle));
    }
}
