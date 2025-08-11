package com.postech.saboresconectados.helpers;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.dto.NewItemDto;
import com.postech.saboresconectados.core.item.dto.UpdateItemDto;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class ItemObjectMother {
    public static ItemEntity buildItemEntity(Map<String, Object> sampleData) {
        return ItemEntity
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString())
                )
                .restaurant(new RestaurantObjectMother().buildRestaurantEntity((Map<String, Object>) sampleData.get("restaurant")))
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price((BigDecimal) sampleData.get("price"))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString())
                )
                .build();
    }

    public static ItemDto buildItemDto(Map<String, Object> sampleData) {
        return ItemDto
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .restaurant(
                        sampleData.get("restaurant") == null
                                ? null
                                : RestaurantObjectMother.buildRestaurantDto((Map<String, Object>) sampleData.get("restaurant")))
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price((BigDecimal) sampleData.get("price"))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }

    public static NewItemDto buildNewItemDto(Map<String, Object> sampleData) {
        return NewItemDto
                .builder()
                .restaurantId(UUID.fromString(sampleData.get("restaurantId").toString()))
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price((BigDecimal) sampleData.get("price"))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .build();
    }

    public static UpdateItemDto buildUpdateItemDto(Map<String, Object> sampleData) {
        return UpdateItemDto
                .builder()
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price((BigDecimal) sampleData.get("price"))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .build();
    }

    public static ItemResponseDto buildItemResponseDto(Map<String, Object> sampleData) {
        return ItemResponseDto
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .restaurantId(UUID.fromString(sampleData.get("restaurantId").toString()))
                .name(sampleData.get("name").toString())
                .description(sampleData.get("description").toString())
                .price(new BigDecimal(sampleData.get("price").toString()))
                .availableOnlyAtRestaurant((Boolean) sampleData.get("availableOnlyAtRestaurant"))
                .photoPath(sampleData.get("photoPath").toString())
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }
}
