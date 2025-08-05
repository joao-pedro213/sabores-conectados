package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.gateways.ItemGateway;
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
