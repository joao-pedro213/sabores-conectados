package com.postech.saboresconectados.core.domain.exceptions;

public class UserNotFoundException extends BusinessException {
    private static final String MESSAGE = "A user with the provided id was not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}
