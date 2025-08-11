package com.postech.saboresconectados.core.restaurant.presenter;

import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.restaurant.dto.DailyScheduleDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.user.presenter.UserPresenter;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.TreeMap;

public class RestaurantPresenter {
    public static RestaurantPresenter build() {
        return new RestaurantPresenter();
    }

    public RestaurantDto toDto(RestaurantEntity restaurant) {
        Map<DayOfWeek, DailyScheduleDto> businessHours = new TreeMap<>();
        restaurant
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
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType().getValue())
                .businessHours(businessHours)
                .owner(UserPresenter.build().toDto(restaurant.getOwner()))
                .lastUpdated(restaurant.getLastUpdated())
                .build();
    }
}
