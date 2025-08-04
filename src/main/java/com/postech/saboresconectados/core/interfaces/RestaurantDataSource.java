package com.postech.saboresconectados.core.interfaces;

import com.postech.saboresconectados.core.dtos.RestaurantDto;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantDataSource {
    RestaurantDto save(RestaurantDto restaurantDto);

    Optional<RestaurantDto> findById(UUID id);

    Optional<RestaurantDto> findByName(String name);

    void deleteById(UUID id);
}
