package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.dtos.ItemDto;

public class ItemPresenter {
    public static ItemPresenter build() {
        return new ItemPresenter();
    }

    public ItemDto toDto(ItemEntity itemEntity) {
        return ItemDto
                .builder()
                .id(itemEntity.getId())
                .restaurant(RestaurantPresenter.build().toDto(itemEntity.getRestaurant()))
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .price(itemEntity.getPrice())
                .availableOnlyAtRestaurant(itemEntity.isAvailableOnlyAtRestaurant())
                .photoPath(itemEntity.getPhotoPath())
                .lastUpdated(itemEntity.getLastUpdated())
                .build();
    }
}
