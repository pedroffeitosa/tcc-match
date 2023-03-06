package com.psoft.match.tcc.util.exception.tcc;

import javax.persistence.EntityNotFoundException;

public class TCCNotFoundException extends EntityNotFoundException {

    private static final String TCC_NOT_FOUND_MSG = "TCC with id %d not found.";

    public TCCNotFoundException(Long id) {
        super(String.format(TCC_NOT_FOUND_MSG, id));
    }
}
