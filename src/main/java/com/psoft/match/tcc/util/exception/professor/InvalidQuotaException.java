package com.psoft.match.tcc.util.exception.professor;

import com.psoft.match.tcc.util.exception.common.BadRequestException;

public class InvalidQuotaException extends BadRequestException {
    private static final String INVALID_QUOTA_MSG = "Quota must be greater than 0.";

    public InvalidQuotaException() {
        super(String.format(INVALID_QUOTA_MSG));
    }
}
