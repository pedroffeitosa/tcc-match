package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class PendingTCCException extends BadRequestException {

    private static final String PENDING_TCC_EXCEPTION_MSG = "TCC with id %d is not approved yet.";

    public PendingTCCException(Long tccId) {
        super(String.format(PENDING_TCC_EXCEPTION_MSG, tccId));
    }
}
