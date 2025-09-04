package com.postech.saboresconectados.core.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder
public class NewRestaurantDto {
    private String name;
    private String address;
    private String cuisineType;
    private Map<DayOfWeek, DailyScheduleDto> businessHours;
    private UUID ownerId;
}
