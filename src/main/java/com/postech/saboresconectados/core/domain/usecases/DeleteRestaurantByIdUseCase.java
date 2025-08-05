package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.gateways.RestaurantGateway;
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
