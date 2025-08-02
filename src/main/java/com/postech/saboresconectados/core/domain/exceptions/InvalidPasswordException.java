package com.postech.saboresconectados.core.domain.exceptions;

public class InvalidPasswordException extends BusinessException {
    private static final String MESSAGE =
            "Password must contain at least one uppercase letter, "
                    + "one lowercase letter, one number, and one special character";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}
