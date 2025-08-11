package com.postech.saboresconectados.core.restaurant.domain.usecase;

import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteRestaurantByIdUseCase {
    private final RestaurantGateway restaurantGateway;

    public static DeleteRestaurantByIdUseCase build(RestaurantGateway restaurantGateway) {
        return new DeleteRestaurantByIdUseCase(restaurantGateway);
    }

    public void execute(UUID id) {
        this.restaurantGateway.deleteById(id);
    }
}
