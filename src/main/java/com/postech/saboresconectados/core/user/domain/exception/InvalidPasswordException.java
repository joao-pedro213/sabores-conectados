package com.postech.saboresconectados.core.user.domain.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;

public class InvalidPasswordException extends BusinessException {
    private static final String MESSAGE =
            "Password must contain at least one uppercase letter, "
                    + "one lowercase letter, one number, and one special character";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
