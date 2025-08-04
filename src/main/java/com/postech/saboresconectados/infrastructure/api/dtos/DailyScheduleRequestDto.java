package com.postech.saboresconectados.infrastructure.api.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class DailyScheduleRequestDto {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
