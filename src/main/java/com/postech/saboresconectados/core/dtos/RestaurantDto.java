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
public class RestaurantDto {
    private UUID id;
    private String name;
    private String address;
    private String cuisineType;
    private Map<DayOfWeek, DailyScheduleDto> businessHours;
    private UserDto owner;
    private LocalDateTime lastUpdated;
}
