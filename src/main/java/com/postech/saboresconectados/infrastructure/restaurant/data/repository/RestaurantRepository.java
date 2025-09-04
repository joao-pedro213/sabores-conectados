package com.postech.saboresconectados.infrastructure.restaurant.data.repository;

import com.postech.saboresconectados.infrastructure.restaurant.data.model.RestaurantModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<RestaurantModel, UUID> {
    Optional<RestaurantModel> findByName(String name);
}
