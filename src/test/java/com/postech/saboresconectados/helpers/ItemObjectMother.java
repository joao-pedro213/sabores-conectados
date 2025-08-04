package com.postech.saboresconectados.helpers;

import com.postech.saboresconectados.core.dtos.NewItemDto;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class ItemObjectMother {
    public static NewItemDto buildINewItemDto(Map<String, Object> sampleData) {
        return NewItemDto
                .builder()
                .restaurantId((UUID) sampleData.get("restaurantId"))
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price((BigDecimal) sampleData.get("price"))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .build();
    }
}
