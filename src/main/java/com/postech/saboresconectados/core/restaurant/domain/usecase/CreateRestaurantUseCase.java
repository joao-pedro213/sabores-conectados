package com.postech.saboresconectados.core.restaurant.domain.usecase;

import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public static CreateRestaurantUseCase build(RestaurantGateway restaurantGateway) {
        return new CreateRestaurantUseCase(restaurantGateway);
    }

    public RestaurantEntity execute(RestaurantEntity restaurantEntity) {
        Optional<RestaurantEntity> foundRestaurant = this.restaurantGateway.findByName(restaurantEntity.getName());
        if (foundRestaurant.isPresent()) {
            throw new EntityAlreadyExistsException("Restaurant");
        }
        return this.restaurantGateway.save(restaurantEntity);
    }
}
