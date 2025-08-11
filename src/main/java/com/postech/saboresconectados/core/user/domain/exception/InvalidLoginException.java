package com.postech.saboresconectados.core.user.domain.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;

public class InvalidLoginException extends BusinessException {
    private static final String MESSAGE = "Login can only contain letters, numbers, '.', '_' and '-'";

    public InvalidLoginException() {
        super(MESSAGE);
    }
}
