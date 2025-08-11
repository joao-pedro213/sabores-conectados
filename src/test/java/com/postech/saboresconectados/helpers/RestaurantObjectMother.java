package com.postech.saboresconectados.helpers;

import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.restaurant.domain.entity.enumerator.CuisineType;
import com.postech.saboresconectados.core.restaurant.dto.DailyScheduleDto;
import com.postech.saboresconectados.core.restaurant.dto.NewRestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.UpdateRestaurantDto;
import com.postech.saboresconectados.core.restaurant.valueobject.DailySchedule;
import com.postech.saboresconectados.infrastructure.restaurant.dto.DailyScheduleResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.RestaurantResponseDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public class RestaurantObjectMother {
    public static RestaurantEntity buildRestaurantEntity(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailySchedule> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailySchedule
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return RestaurantEntity
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .name(sampleData.get("name").toString())
                .address(sampleData.get("address").toString())
                .cuisineType(CuisineType.fromValue(sampleData.get("cuisineType").toString()))
                .businessHours(businessHours)
                .owner(new UserObjectMother().createSampleUser((Map<String, Object>) sampleData.get("owner")))
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }

    public static NewRestaurantDto buildNewRestaurantDto(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailyScheduleDto
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return NewRestaurantDto
                .builder()
                .name(sampleData.get("name").toString())
                .address(sampleData.get("address").toString())
                .cuisineType(sampleData.get("cuisineType").toString())
                .businessHours(businessHours)
                .ownerId(UUID.fromString(sampleData.get("ownerId").toString()))
                .build();
    }

    public static UpdateRestaurantDto buildUpdateRestaurantDto(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailyScheduleDto
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return UpdateRestaurantDto
                .builder()
                .address(sampleData.get("address").toString())
                .businessHours(businessHours)
                .build();
    }

    public static RestaurantDto buildRestaurantDto(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailyScheduleDto> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailyScheduleDto
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return RestaurantDto
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .name(sampleData.get("name").toString())
                .address(sampleData.get("address").toString())
                .cuisineType(sampleData.get("cuisineType").toString())
                .businessHours(businessHours)
                .owner(new UserObjectMother().createSampleUserDto((Map<String, Object>) sampleData.get("owner")))
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }

    public static RestaurantResponseDto buildRestaurantResponseDto(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailyScheduleResponseDto> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailyScheduleResponseDto
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return RestaurantResponseDto
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .name(sampleData.get("name").toString())
                .address(sampleData.get("address").toString())
                .cuisineType(sampleData.get("cuisineType").toString())
                .businessHours(businessHours)
                .ownerId(UUID.fromString(sampleData.get("ownerId").toString()))
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }
}
