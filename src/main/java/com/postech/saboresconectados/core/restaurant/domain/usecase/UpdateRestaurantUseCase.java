package com.postech.saboresconectados.core.restaurant.domain.usecase;

import com.postech.saboresconectados.core.restaurant.valueobject.DailySchedule;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class UpdateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public static UpdateRestaurantUseCase build(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway);
    }

    public RestaurantEntity execute(UUID id, String address, Map<DayOfWeek, DailySchedule> businessHours) {
        RestaurantEntity foundRestaurant = this.restaurantGateway
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant"));
        RestaurantEntity restaurantWithUpdates = foundRestaurant
                .toBuilder()
                .address(address)
                .businessHours(businessHours)
                .build();
        return this.restaurantGateway.save(restaurantWithUpdates);
    }
}
