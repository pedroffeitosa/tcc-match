package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class InvalidTermException extends BadRequestException {

    private static final String INVALID_TERM_EXCEPTION_MSG = "Invalid term format %s.";

    public InvalidTermException(String term) {
        super(String.format(INVALID_TERM_EXCEPTION_MSG, term));
    }
}
