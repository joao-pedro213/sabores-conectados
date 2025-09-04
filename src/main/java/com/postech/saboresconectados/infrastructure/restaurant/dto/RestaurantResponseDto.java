package com.postech.saboresconectados.infrastructure.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class RestaurantResponseDto {
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private Map<DayOfWeek, DailyScheduleResponseDto> businessHours;
    private UUID ownerId;
    private LocalDateTime lastUpdated;
}
