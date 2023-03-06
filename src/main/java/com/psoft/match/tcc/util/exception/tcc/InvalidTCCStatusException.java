package com.psoft.match.tcc.util.exception.tcc;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class InvalidTCCStatusException extends BadRequestException {

    private static final String INVALID_TCC_STATUS_MSG = "Invalid TCC status description: %s";

    public InvalidTCCStatusException(String status) {
        super(String.format(INVALID_TCC_STATUS_MSG, status));
    }
}
