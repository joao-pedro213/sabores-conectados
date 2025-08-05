package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class UpdateItemUseCase {
    private ItemGateway itemGateway;

    public static UpdateItemUseCase build(ItemGateway itemGateway) {
        return new UpdateItemUseCase(itemGateway);
    }

    public ItemEntity execute(
            UUID id, String name, String description,
            BigDecimal price, Boolean availableOnlyAtRestaurant, String photoPath) {
        ItemEntity foundItem = this.itemGateway
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Item"));
        ItemEntity itemWithUpdates = foundItem
                .toBuilder()
                .name(name)
                .description(description)
                .price(price)
                .availableOnlyAtRestaurant(availableOnlyAtRestaurant)
                .photoPath(photoPath)
                .build();
        return this.itemGateway.save(itemWithUpdates);
    }
}
