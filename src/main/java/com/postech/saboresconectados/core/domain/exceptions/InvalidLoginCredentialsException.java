package com.postech.saboresconectados.core.domain.exceptions;

public class InvalidLoginCredentialsException extends BusinessException {
    private static final String MESSAGE = "The username and/or password are invalid";

    public InvalidLoginCredentialsException() {
        super(MESSAGE);
    }
}
