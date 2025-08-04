package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class RestaurantGateway {
    private final RestaurantDataSource dataSource;

    public static RestaurantGateway create(RestaurantDataSource dataSource) {
        return new RestaurantGateway(dataSource);
    }

    public Restaurant save(Restaurant restaurant) {
        RestaurantDto restaurantToSave = this.toDto(restaurant);
        RestaurantDto savedRestaurant = this.dataSource.save(restaurantToSave);
        return this.toDomain(savedRestaurant);
    }

    public Optional<Restaurant> findById(UUID id) {
        Optional<RestaurantDto> foundRestaurant = this.dataSource.findById(id);
        return foundRestaurant.map(this::toDomain);
    }

    public Optional<Restaurant> findByName(String name) {
        Optional<RestaurantDto> foundRestaurant = this.dataSource.findByName(name);
        return foundRestaurant.map(this::toDomain);
    }

    public void deleteById(UUID id) {
        this.dataSource.deleteById(id);
    }

    private RestaurantDto toDto(Restaurant restaurant) {
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        restaurant
                .getBusinessHours()
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailyScheduleDto
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return RestaurantDto
                .builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .cuisineType(restaurant.getCuisineType().getValue())
                .businessHours(businessHours)
                .owner(this.toUserDto(restaurant.getOwner()))
                .lastUpdated(restaurant.getLastUpdated())
                .build();
    }

    private UserDto toUserDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .userType(user.getUserType().getValue())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .lastUpdated(user.getLastUpdated())
                .build();
    }

    private Restaurant toDomain(RestaurantDto restaurantDto) {
        Map<DayOfWeek, DailySchedule> businessHours = new LinkedHashMap<>();
        restaurantDto
                .getBusinessHours()
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailySchedule
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return Restaurant
                .builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .cuisineType(CuisineType.fromValue(restaurantDto.getCuisineType()))
                .businessHours(businessHours)
                .owner(this.toUser(restaurantDto.getOwner()))
                .lastUpdated(restaurantDto.getLastUpdated())
                .build();
    }

    private User toUser(UserDto userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .userType(UserType.fromValue(userDto.getUserType()))
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .lastUpdated(userDto.getLastUpdated())
                .build();
    }
}
