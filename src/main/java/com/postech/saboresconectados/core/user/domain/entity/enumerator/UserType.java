package com.postech.saboresconectados.core.user.domain.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum UserType {
    RESTAURANT_OWNER("Restaurant Owner"),
    CUSTOMER("Customer");

    private final String value;

    public static UserType fromValue(String value) {
        return Arrays
                .stream(UserType.values())
                .filter((type) -> type.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid UserType provided"));
    }
}
