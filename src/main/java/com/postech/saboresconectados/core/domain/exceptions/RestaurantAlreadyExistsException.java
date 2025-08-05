package com.postech.saboresconectados.core.domain.exceptions;

public class RestaurantAlreadyExistsException extends BusinessException {
    private static final String MESSAGE = "A restaurant with the provided name already exists";

    public RestaurantAlreadyExistsException() {
        super(MESSAGE);
    }
}
