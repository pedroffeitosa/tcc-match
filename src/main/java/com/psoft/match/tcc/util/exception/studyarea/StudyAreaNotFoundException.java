package com.psoft.match.tcc.util.exception.studyarea;

import javax.persistence.EntityNotFoundException;

public class StudyAreaNotFoundException extends EntityNotFoundException  {
    private static final String STUDY_AREA_NOT_FOUND_MSG = "Study area with id %d not found.";
    private static final String STUDY_AREA_DESCRIPTION_NOT_FOUND_MSG = "Study area with description %s not found.";

    public StudyAreaNotFoundException(Long id) {
        super(String.format(STUDY_AREA_NOT_FOUND_MSG, id));
    }

    public StudyAreaNotFoundException(String description) {
        super(String.format(STUDY_AREA_DESCRIPTION_NOT_FOUND_MSG, description));
    }
}
