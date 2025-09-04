package com.postech.saboresconectados.infrastructure.item.data.mapper;

import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.dto.NewItemDto;
import com.postech.saboresconectados.core.item.dto.UpdateItemDto;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.item.dto.NewItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.dto.UpdateItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.data.model.ItemModel;
import com.postech.saboresconectados.infrastructure.restaurant.data.mapper.RestaurantMapper;
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
