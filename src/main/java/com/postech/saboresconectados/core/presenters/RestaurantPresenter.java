package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeMap;

public class RestaurantPresenter {
    public static RestaurantPresenter create() {
        return new RestaurantPresenter();
    }

    public RestaurantDto toDto(RestaurantEntity restaurantEntity) {
        Map<DayOfWeek, DailyScheduleDto> businessHours = new TreeMap<>();
        restaurantEntity
                .getBusinessHours()
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailyScheduleDto
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return RestaurantDto
                .builder()
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .address(restaurantEntity.getAddress())
                .cuisineType(restaurantEntity.getCuisineType().getValue())
                .businessHours(businessHours)
                .owner(UserPresenter.create().toDto(restaurantEntity.getOwner()))
                .lastUpdated(restaurantEntity.getLastUpdated())
                .build();
    }
}
