package com.postech.saboresconectados.core.user.domain.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;

public class InvalidEmailException extends BusinessException {
    private static final String MESSAGE = "Email should have a valid format";

    public InvalidEmailException() {
        super(MESSAGE);
    }
}
