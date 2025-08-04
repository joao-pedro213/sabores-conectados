package com.postech.saboresconectados.core.domain.exceptions;

public class UserNotRestaurantOwnerException extends BusinessException {
    private static final String MESSAGE = "Only a RESTAURANT_OWNER user can be associated with a Restaurant";

    public UserNotRestaurantOwnerException() {
        super(MESSAGE);
    }
}
