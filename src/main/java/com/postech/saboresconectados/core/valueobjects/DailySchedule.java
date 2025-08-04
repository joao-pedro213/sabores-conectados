package com.postech.saboresconectados.core.valueobjects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@Builder(toBuilder = true)
public class DailySchedule {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
