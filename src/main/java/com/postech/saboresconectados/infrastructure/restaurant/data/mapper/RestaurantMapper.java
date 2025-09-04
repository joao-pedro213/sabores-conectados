package com.postech.saboresconectados.infrastructure.restaurant.data.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postech.saboresconectados.core.restaurant.dto.DailyScheduleDto;
import com.postech.saboresconectados.core.restaurant.dto.NewRestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.UpdateRestaurantDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.DailyScheduleRequestDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.DailyScheduleResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.RestaurantResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.restaurant.data.model.RestaurantModel;
import com.postech.saboresconectados.infrastructure.user.data.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RestaurantMapper {

    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static RestaurantModel toRestaurantModel(RestaurantDto restaurantDto) {
        String businessHoursJson;
        try {
            businessHoursJson = objectMapper.writeValueAsString(restaurantDto.getBusinessHours());
        } catch (JsonProcessingException exception) {
            businessHoursJson = null;
        }
        return RestaurantModel
                .builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .cuisineType(restaurantDto.getCuisineType())
                .businessHours(businessHoursJson)
                .owner(UserMapper.toUserModel(restaurantDto.getOwner()))
                .lastUpdated(restaurantDto.getLastUpdated())
                .build();
    }

    public static RestaurantDto toRestaurantDto(RestaurantModel restaurantModel) {
        Map<DayOfWeek, DailyScheduleDto> businessHours;
        try {
            TypeReference<Map<DayOfWeek, DailyScheduleDto>> typeReference = new TypeReference<>() {
            };
            businessHours = objectMapper.readValue(restaurantModel.getBusinessHours(), typeReference);
        } catch (JsonProcessingException exception) {
            businessHours = null;
        }
        return RestaurantDto
                .builder()
                .id(restaurantModel.getId())
                .name(restaurantModel.getName())
                .address(restaurantModel.getAddress())
                .cuisineType(restaurantModel.getCuisineType())
                .businessHours(businessHours)
                .owner(UserMapper.toUserDto(restaurantModel.getOwner()))
                .lastUpdated(restaurantModel.getLastUpdated())
                .build();
    }

    public static NewRestaurantDto toNewRestaurantDto(NewRestaurantRequestDto newRestaurantRequestDto) {
        return NewRestaurantDto
                .builder()
                .name(newRestaurantRequestDto.getName())
                .address(newRestaurantRequestDto.getAddress())
                .cuisineType(newRestaurantRequestDto.getCuisineType())
                .businessHours(toBusinessHoursDto(newRestaurantRequestDto.getBusinessHours()))
                .ownerId(UUID.fromString(newRestaurantRequestDto.getOwnerId()))
                .build();
    }

    private static Map<DayOfWeek, DailyScheduleDto> toBusinessHoursDto(
            Map<DayOfWeek, DailyScheduleRequestDto> businessHoursRequestDto) {
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        businessHoursRequestDto
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailyScheduleDto
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return businessHours;
    }

    public static UpdateRestaurantDto toUpdateRestaurantDto(UpdateRestaurantRequestDto updateRestaurantRequestDto) {
        return UpdateRestaurantDto
                .builder()
                .address(updateRestaurantRequestDto.getAddress())
                .businessHours(toBusinessHoursDto(updateRestaurantRequestDto.getBusinessHours()))
                .build();
    }

    public static RestaurantResponseDto toRestaurantResponseDto(RestaurantDto restaurantDto) {
        Map<DayOfWeek, DailyScheduleResponseDto> businessHours = new LinkedHashMap<>();
        restaurantDto
                .getBusinessHours()
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailyScheduleResponseDto
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return RestaurantResponseDto
                .builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .cuisineType(restaurantDto.getCuisineType())
                .businessHours(businessHours)
                .ownerId(restaurantDto.getOwner().getId())
                .lastUpdated(restaurantDto.getLastUpdated())
                .build();
    }
}
