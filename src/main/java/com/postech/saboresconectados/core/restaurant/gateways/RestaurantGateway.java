package com.postech.saboresconectados.core.restaurant.gateways;

import com.postech.saboresconectados.core.restaurant.valueobject.DailySchedule;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.restaurant.domain.entity.enumerator.CuisineType;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.restaurant.dto.DailyScheduleDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.core.restaurant.datasource.RestaurantDataSource;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class RestaurantGateway {
    private final RestaurantDataSource dataSource;

    public static RestaurantGateway build(RestaurantDataSource dataSource) {
        return new RestaurantGateway(dataSource);
    }

    public RestaurantEntity save(RestaurantEntity restaurantEntity) {
        RestaurantDto restaurantToSave = this.toDto(restaurantEntity);
        RestaurantDto savedRestaurant = this.dataSource.save(restaurantToSave);
        return this.toEntity(savedRestaurant);
    }

    public Optional<RestaurantEntity> findById(UUID id) {
        Optional<RestaurantDto> foundRestaurant = this.dataSource.findById(id);
        return foundRestaurant.map(this::toEntity);
    }

    public Optional<RestaurantEntity> findByName(String name) {
        Optional<RestaurantDto> foundRestaurant = this.dataSource.findByName(name);
        return foundRestaurant.map(this::toEntity);
    }

    public void deleteById(UUID id) {
        this.dataSource.deleteById(id);
    }

    private RestaurantDto toDto(RestaurantEntity restaurantEntity) {
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        restaurantEntity
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
                .id(restaurantEntity.getId())
                .name(restaurantEntity.getName())
                .address(restaurantEntity.getAddress())
                .cuisineType(restaurantEntity.getCuisineType().getValue())
                .businessHours(businessHours)
                .owner(this.toUserDto(restaurantEntity.getOwner()))
                .lastUpdated(restaurantEntity.getLastUpdated())
                .build();
    }

    private UserDto toUserDto(UserEntity userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .userType(userEntity.getUserType().getValue())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .address(userEntity.getAddress())
                .lastUpdated(userEntity.getLastUpdated())
                .build();
    }

    private RestaurantEntity toEntity(RestaurantDto restaurantDto) {
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
        return RestaurantEntity
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

    private UserEntity toUser(UserDto userDto) {
        return UserEntity
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
