package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.usecases.CreateItemUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteItemByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateItemUseCase;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewItemDto;
import com.postech.saboresconectados.core.dtos.UpdateItemDto;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.core.presenters.ItemPresenter;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ItemController {
    private ItemDataSource itemDataSource;
    private RestaurantDataSource restaurantDataSource;

    public static ItemController build(ItemDataSource itemDataSource, RestaurantDataSource restaurantDataSource) {
        return new ItemController(itemDataSource, restaurantDataSource);
    }

    public ItemDto createItem(NewItemDto newItemDto) {
        ItemGateway itemGateway = ItemGateway.build(this.itemDataSource);
        RestaurantGateway restaurantGateway = RestaurantGateway.build(this.restaurantDataSource);
        CreateItemUseCase useCase = CreateItemUseCase.build(itemGateway, restaurantGateway);
        ItemEntity createdItem = useCase
                .execute(
                        newItemDto.getRestaurantId(),
                        newItemDto.getName(),
                        newItemDto.getDescription(),
                        newItemDto.getPrice(),
                        newItemDto.getAvailableOnlyAtRestaurant(),
                        newItemDto.getPhotoPath());
        ItemPresenter presenter = ItemPresenter.build();
        return presenter.toDto(createdItem);
    }

    public ItemDto updateItem(UUID id, UpdateItemDto updateItemDto) {
        ItemGateway itemGateway = ItemGateway.build(this.itemDataSource);
        UpdateItemUseCase useCase = UpdateItemUseCase.build(itemGateway);
        ItemEntity updatedItem = useCase.execute(
                id,
                updateItemDto.getName(),
                updateItemDto.getDescription(),
                updateItemDto.getPrice(),
                updateItemDto.getAvailableOnlyAtRestaurant(),
                updateItemDto.getPhotoPath());
        ItemPresenter presenter = ItemPresenter.build();
        return presenter.toDto(updatedItem);
    }

    public void deleteItemById(UUID id) {
        ItemGateway itemGateway = ItemGateway.build(this.itemDataSource);
        DeleteItemByIdUseCase useCase = DeleteItemByIdUseCase.build(itemGateway);
        useCase.execute(id);
    }
}
