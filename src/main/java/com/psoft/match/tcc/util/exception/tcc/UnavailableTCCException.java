package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class UnavailableTCCException extends BadRequestException {

    private static final String UNAVAILABLE_TCC_MSG = "The TCC with id %d is not available.";

    public UnavailableTCCException(Long id) {
        super(String.format(UNAVAILABLE_TCC_MSG, id));
    }
}
