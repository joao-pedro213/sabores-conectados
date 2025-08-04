package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RetrieveRestaurantByIdUseCase {
    private final RestaurantGateway restaurantGateway;

    public static RetrieveRestaurantByIdUseCase create(RestaurantGateway restaurantGateway) {
        return new RetrieveRestaurantByIdUseCase(restaurantGateway);
    }

    public Restaurant execute(UUID id) {
        return this.restaurantGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant"));
    }
}
