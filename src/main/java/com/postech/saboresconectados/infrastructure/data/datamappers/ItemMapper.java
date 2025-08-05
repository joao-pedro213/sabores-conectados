package com.postech.saboresconectados.infrastructure.data.datamappers;

import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewItemDto;
import com.postech.saboresconectados.core.dtos.UpdateItemDto;
import com.postech.saboresconectados.infrastructure.api.dtos.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewItemRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateItemRequestDto;
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
                .availableOnlyAtRestaurant(itemDto.getAvailableOnlyAtRestaurant())
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
                .availableOnlyAtRestaurant(itemModel.getAvailableOnlyAtRestaurant())
                .photoPath(itemModel.getPhotoPath())
                .lastUpdated(itemModel.getLastUpdated())
                .build();
    }

    public static NewItemDto toNewItemDto(NewItemRequestDto newItemRequestDto) {
        return NewItemDto
                .builder()
                .restaurantId(newItemRequestDto.getRestaurantId())
                .name(newItemRequestDto.getName())
                .description(newItemRequestDto.getDescription())
                .price(newItemRequestDto.getPrice())
                .availableOnlyAtRestaurant(newItemRequestDto.getAvailableOnlyAtRestaurant())
                .photoPath(newItemRequestDto.getPhotoPath())
                .build();
    }

    public static UpdateItemDto toUpdateItemDto(UpdateItemRequestDto updateItemRequestDto) {
        return UpdateItemDto
                .builder()
                .name(updateItemRequestDto.getName())
                .description(updateItemRequestDto.getDescription())
                .price(updateItemRequestDto.getPrice())
                .availableOnlyAtRestaurant(updateItemRequestDto.getAvailableOnlyAtRestaurant())
                .photoPath(updateItemRequestDto.getPhotoPath())
                .build();
    }

    public static ItemResponseDto toItemResponseDto(ItemDto itemDto) {
        return ItemResponseDto
                .builder()
                .id(itemDto.getId())
                .restaurantId(itemDto.getRestaurant().getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .price(itemDto.getPrice())
                .availableOnlyAtRestaurant(itemDto.getAvailableOnlyAtRestaurant())
                .photoPath(itemDto.getPhotoPath())
                .lastUpdated(itemDto.getLastUpdated())
                .build();
    }
}
