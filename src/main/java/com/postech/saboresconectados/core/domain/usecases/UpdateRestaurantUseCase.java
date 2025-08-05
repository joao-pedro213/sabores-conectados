package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.valueobjects.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
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
