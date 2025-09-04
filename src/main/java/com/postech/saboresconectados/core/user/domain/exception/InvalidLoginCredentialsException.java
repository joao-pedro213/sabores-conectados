package com.postech.saboresconectados.core.user.domain.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;

public class InvalidLoginCredentialsException extends BusinessException {
    private static final String MESSAGE = "The username and/or password are invalid";

    public InvalidLoginCredentialsException() {
        super(MESSAGE);
    }
}
