package com.postech.saboresconectados.infrastructure.restaurant.data;

import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.datasource.RestaurantDataSource;
import com.postech.saboresconectados.infrastructure.restaurant.data.mapper.RestaurantMapper;
import com.postech.saboresconectados.infrastructure.restaurant.data.model.RestaurantModel;
import com.postech.saboresconectados.infrastructure.restaurant.data.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class RestaurantDataSourceJpa implements RestaurantDataSource {
    private final RestaurantRepository repository;

    @Override
    public RestaurantDto save(RestaurantDto restaurantDto) {
        RestaurantModel restaurantToSave = RestaurantMapper.toRestaurantModel(restaurantDto);
        RestaurantModel savedRestaurant = this.repository.save(restaurantToSave);
        return RestaurantMapper.toRestaurantDto(savedRestaurant);
    }

    @Override
    public Optional<RestaurantDto> findById(UUID id) {
        Optional<RestaurantModel> foundRestaurant = this.repository.findById(id);
        return foundRestaurant.map(RestaurantMapper::toRestaurantDto);
    }

    @Override
    public Optional<RestaurantDto> findByName(String name) {
        Optional<RestaurantModel> foundRestaurant = this.repository.findByName(name);
        return foundRestaurant.map(RestaurantMapper::toRestaurantDto);
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }
}
