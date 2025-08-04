package com.postech.saboresconectados.helpers;

import com.postech.saboresconectados.core.domain.entities.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.DailyScheduleOutputDto;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

public class RestaurantObjectMother {
    public Restaurant createSampleRestaurant(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailySchedule> businessHours = new LinkedHashMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailySchedule
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return Restaurant
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

    public NewRestaurantDto createSampleNewRestaurantDto(Map<String, Object> sampleData) {
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

    public UpdateRestaurantDto createSampleUpdateRestaurantDto(Map<String, Object> sampleData) {
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

    public RestaurantDto createSampleRestaurantDto(Map<String, Object> sampleData) {
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

    public RestaurantOutputDto createSampleRestaurantOutputDto(Map<String, Object> sampleData) {
        Map<Object, Map<Object, Object>> businessHoursMap = (Map<Object, Map<Object, Object>>) sampleData.get("businessHours");
        Map<DayOfWeek, DailyScheduleOutputDto> businessHours = new TreeMap<>();
        businessHoursMap.forEach((k, v) ->
                businessHours.put(
                        DayOfWeek.valueOf(k.toString()),
                        DailyScheduleOutputDto
                                .builder()
                                .openingTime(LocalTime.parse(v.get("openingTime").toString()))
                                .closingTime(LocalTime.parse(v.get("closingTime").toString())).build()));
        return RestaurantOutputDto
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
