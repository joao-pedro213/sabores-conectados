package com.postech.saboresconectados.core.domain.exceptions;

public class EntityNotFoundException extends BusinessException {
    private static final String MESSAGE = "The %s with the provided identifier was not found";

    public EntityNotFoundException(String entityName) {
        super(MESSAGE.formatted(entityName));
    }
}
