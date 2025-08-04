package com.postech.saboresconectados.infrastructure.api.dtos;

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
public class UpdateRestaurantRequestDto {
    @NotBlank
    private String address;

    @NotNull
    private Map<DayOfWeek, DailyScheduleRequestDto> businessHours;
}
