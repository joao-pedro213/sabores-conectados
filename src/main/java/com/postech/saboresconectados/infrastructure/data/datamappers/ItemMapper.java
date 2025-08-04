package com.postech.saboresconectados.infrastructure.data.datamappers;

import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ItemMapper {
    public static ItemModel toItemModel(ItemDto itemDto) {
        return ItemModel
                .builder()
                .id(itemDto.getId())
                .restaurant(RestaurantMapper.toRestaurantModel(itemDto.getRestaurant()))
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .availableOnlyAtRestaurant(itemDto.isAvailableOnlyAtRestaurant())
                .photoPath(itemDto.getPhotoPath())
                .lastUpdated(itemDto.getLastUpdated())
                .build();
    }

    public static ItemDto toItemDto(ItemModel itemModel) {
        return ItemDto
                .builder()
                .id(itemModel.getId())
                .restaurant(RestaurantMapper.toRestaurantDto(itemModel.getRestaurant()))
                .name(itemModel.getName())
                .description(itemModel.getDescription())
                .price(itemModel.getPrice())
                .availableOnlyAtRestaurant(itemModel.isAvailableOnlyAtRestaurant())
                .photoPath(itemModel.getPhotoPath())
                .lastUpdated(itemModel.getLastUpdated())
                .build();
    }
}
