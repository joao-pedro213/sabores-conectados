package com.postech.saboresconectados.core.domain.exceptions;

public class InvalidEmailException extends BusinessException {
    private static final String MESSAGE = "Email should have a valid format";

    public InvalidEmailException() {
        super(MESSAGE);
    }
}
