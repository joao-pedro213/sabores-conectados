package com.postech.saboresconectados.core.restaurant.domain.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum CuisineType {
    ITALIAN("Italian"),
    MEXICAN("Mexican"),
    CHINESE("Chinese"),
    INDIAN("Indian"),
    JAPANESE("Japanese"),
    THAI("Thai"),
    FRENCH("French"),
    BRAZILIAN("Brazilian"),
    AMERICAN("American"),
    GREEK("Greek"),
    VIETNAMESE("Vietnamese"),
    SPANISH("Spanish");

    private final String value;

    public static CuisineType fromValue(String value) {
        return Arrays
                .stream(CuisineType.values())
                .filter((type) -> type.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid CuisineType provided: " + value));
    }
}
