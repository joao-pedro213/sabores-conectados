package com.postech.saboresconectados.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class NewItemDto {
    private UUID restaurantId;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean availableOnlyAtRestaurant;
    private String photoPath;
}
