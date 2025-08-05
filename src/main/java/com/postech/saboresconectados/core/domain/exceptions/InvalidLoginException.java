package com.postech.saboresconectados.core.domain.exceptions;

public class InvalidLoginException extends BusinessException {
    private static final String MESSAGE = "Login can only contain letters, numbers, '.', '_' and '-'";

    public InvalidLoginException() {
        super(MESSAGE);
    }
}
