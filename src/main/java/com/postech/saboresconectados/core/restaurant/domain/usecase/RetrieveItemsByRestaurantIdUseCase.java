package com.postech.saboresconectados.core.restaurant.domain.usecase;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.item.gateway.ItemGateway;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RetrieveItemsByRestaurantIdUseCase {
    private ItemGateway itemGateway;

    public static RetrieveItemsByRestaurantIdUseCase build(ItemGateway itemGateway) {
        return new RetrieveItemsByRestaurantIdUseCase(itemGateway);
    }

    public List<ItemEntity> execute(UUID restaurantId) {
        return this.itemGateway.findAllByRestaurantId(restaurantId);
    }
}
