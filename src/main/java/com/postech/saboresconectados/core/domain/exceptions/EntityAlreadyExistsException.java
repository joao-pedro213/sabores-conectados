package com.postech.saboresconectados.core.domain.exceptions;

public class EntityAlreadyExistsException extends BusinessException {
    private static final String MESSAGE = "The %s with the provided identifier already exists";

    public EntityAlreadyExistsException(String entityName) {
        super(MESSAGE.formatted(entityName));
    }
}
