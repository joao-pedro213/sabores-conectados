package com.postech.saboresconectados.core.item.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder(toBuilder = true)
public class UpdateItemDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Boolean availableOnlyAtRestaurant;
    private String photoPath;
}
