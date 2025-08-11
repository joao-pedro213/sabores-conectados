package com.postech.saboresconectados.core.item.presenter;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.restaurant.presenter.RestaurantPresenter;

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
