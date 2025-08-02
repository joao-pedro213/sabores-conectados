package com.postech.saboresconectados.core.domain.exceptions;

public class UserAlreadyExistsException extends BusinessException {
    private static final String MESSAGE = "A user with the provided login already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}
