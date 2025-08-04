package com.postech.saboresconectados.infrastructure.api.dtos;

import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.infrastructure.api.validator.InEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Map;

@Getter
@Setter
@Builder
public class NewRestaurantRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @InEnum(enumClass = CuisineType.class)
    private String cuisineType;

    @NotNull
    private Map<DayOfWeek, DailyScheduleRequestDto> businessHours;

    @NotBlank
    private String ownerId;
}
