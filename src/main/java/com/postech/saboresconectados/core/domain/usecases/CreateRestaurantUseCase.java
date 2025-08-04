package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.Restaurant;
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

    public Restaurant execute(Restaurant restaurant) {
        Optional<Restaurant> foundRestaurant = this.restaurantGateway.findByName(restaurant.getName());
        if (foundRestaurant.isPresent()) {
            throw new RestaurantAlreadyExistsException();
        }
        return this.restaurantGateway.save(restaurant);
    }
}
