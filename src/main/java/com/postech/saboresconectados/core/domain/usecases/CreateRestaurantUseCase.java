package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.exceptions.RestaurantAlreadyExistsException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public static CreateRestaurantUseCase create(RestaurantGateway restaurantGateway) {
        return new CreateRestaurantUseCase(restaurantGateway);
    }

    public RestaurantEntity execute(RestaurantEntity restaurantEntity) {
        Optional<RestaurantEntity> foundRestaurant = this.restaurantGateway.findByName(restaurantEntity.getName());
        if (foundRestaurant.isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        return this.restaurantGateway.save(restaurantEntity);
    }
}
