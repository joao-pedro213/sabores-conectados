package com.postech.saboresconectados.core.dtos;

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
public class RestaurantOutputDto {
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private Map<DayOfWeek, DailyScheduleOutputDto> businessHours;
    private UUID ownerId;
    private LocalDateTime lastUpdated;
}
