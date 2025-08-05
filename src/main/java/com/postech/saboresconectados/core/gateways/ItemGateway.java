package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.core.valueobjects.DailySchedule;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class ItemGateway {
    private ItemDataSource dataSource;

    public static ItemGateway build(ItemDataSource dataSource) {
        return new ItemGateway(dataSource);
    }

    public ItemEntity save(ItemEntity itemEntity) {
        ItemDto itemDto = this.dataSource.save(this.toItemDto(itemEntity));
        return this.toItemEntity(itemDto);
    }

    public Optional<ItemEntity> findById(UUID id) {
        Optional<ItemDto> foundItem = this.dataSource.findById(id);
        return foundItem.map(this::toItemEntity);
    }

    public List<ItemEntity> findAllByRestaurantId(UUID restaurantId) {
        return this.dataSource.findAllByRestaurantId(restaurantId).stream().map(this::toItemEntity).toList();
    }

    public void deleteById(UUID id) {
        this.dataSource.deleteById(id);
    }

    private ItemDto toItemDto(ItemEntity itemEntity) {
        return ItemDto
                .builder()
                .id(itemEntity.getId())
                .restaurant(this.toRestaurantDto(itemEntity.getRestaurant()))
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .availableOnlyAtRestaurant(itemEntity.isAvailableOnlyAtRestaurant())
                .photoPath(itemEntity.getPhotoPath())
                .lastUpdated(itemEntity.getLastUpdated())
                .build();
    }

    private RestaurantDto toRestaurantDto(RestaurantEntity restaurantEntity) {
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

    private ItemEntity toItemEntity(ItemDto itemDto) {
        return ItemEntity
                .builder()
                .id(itemDto.getId())
                .restaurant(this.toRestaurantEntity(itemDto.getRestaurant()))
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .availableOnlyAtRestaurant(itemDto.getAvailableOnlyAtRestaurant())
                .photoPath(itemDto.getPhotoPath())
                .lastUpdated(itemDto.getLastUpdated())
                .build();
    }

    private RestaurantEntity toRestaurantEntity(RestaurantDto restaurantDto) {
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
                .owner(this.toUserEntity(restaurantDto.getOwner()))
                .lastUpdated(restaurantDto.getLastUpdated())
                .build();
    }

    private UserEntity toUserEntity(UserDto userDto) {
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
