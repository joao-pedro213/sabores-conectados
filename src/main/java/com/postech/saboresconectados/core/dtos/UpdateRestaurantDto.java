package com.postech.saboresconectados.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.util.Map;

@Getter
@Setter
@Builder
public class UpdateRestaurantDto {
    private String address;
    private Map<DayOfWeek, DailyScheduleDto> businessHours;
}
