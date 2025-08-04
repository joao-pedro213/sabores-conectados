package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.dtos.DailyScheduleOutputDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeMap;

public class RestaurantPresenter {
    public static RestaurantPresenter create() {
        return new RestaurantPresenter();
    }

    public RestaurantOutputDto toDto(Restaurant restaurant) {
        Map<DayOfWeek, DailyScheduleOutputDto> businessHours = new TreeMap<>();
        restaurant
                .getBusinessHours()
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailyScheduleOutputDto
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return RestaurantOutputDto
                .builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType().getValue())
                .businessHours(businessHours)
                .ownerId(restaurant.getOwner().getId())
                .lastUpdated(restaurant.getLastUpdated())
                .build();
    }
}
