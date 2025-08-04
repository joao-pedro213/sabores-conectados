package com.postech.saboresconectados.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class DailyScheduleOutputDto {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
