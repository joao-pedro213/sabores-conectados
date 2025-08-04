package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.usecases.CreateItemUseCase;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewItemDto;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.core.presenters.ItemPresenter;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ItemController {
    private ItemDataSource itemDataSource;
    private RestaurantDataSource restaurantDataSource;

    public static ItemController build(ItemDataSource itemDataSource, RestaurantDataSource restaurantDataSource) {
        return new ItemController(itemDataSource, restaurantDataSource);
    }

    public ItemDto createItem(NewItemDto newItemDto) {
        ItemGateway itemGateway = ItemGateway.build(this.itemDataSource);
        RestaurantGateway restaurantGateway = RestaurantGateway.create(this.restaurantDataSource);
        CreateItemUseCase useCase = CreateItemUseCase.build(itemGateway, restaurantGateway);
        ItemEntity itemEntity = useCase
                .execute(
                        newItemDto.getRestaurantId(),
                        newItemDto.getName(),
                        newItemDto.getDescription(),
                        newItemDto.getPrice(),
                        newItemDto.isAvailableOnlyAtRestaurant(),
                        newItemDto.getPhotoPath());
        ItemPresenter presenter = ItemPresenter.build();
        return presenter.toDto(itemEntity);
    }
}
