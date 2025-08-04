package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class UpdateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public static UpdateRestaurantUseCase create(RestaurantGateway restaurantGateway) {
        return new UpdateRestaurantUseCase(restaurantGateway);
    }

    public Restaurant execute(UUID id, String address, Map<DayOfWeek, DailySchedule> businessHours) {
        Restaurant foundRestaurant = this.restaurantGateway
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant"));
        Restaurant restaurantWithUpdates = foundRestaurant
                .toBuilder()
                .address(address)
                .businessHours(businessHours)
                .build();
        return this.restaurantGateway.save(restaurantWithUpdates);
    }
}
