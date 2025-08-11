package com.postech.saboresconectados.infrastructure.restaurant.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class DailyScheduleResponseDto {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
