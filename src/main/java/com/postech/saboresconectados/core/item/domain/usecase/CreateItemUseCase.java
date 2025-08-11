package com.postech.saboresconectados.core.item.domain.usecase;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.item.gateway.ItemGateway;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
public class CreateItemUseCase {
    private ItemGateway itemGateway;
    private RestaurantGateway restaurantGateway;

    public static CreateItemUseCase build(ItemGateway itemGateway, RestaurantGateway restaurantGateway) {
        return new CreateItemUseCase(itemGateway, restaurantGateway);
    }

    public ItemEntity execute(
            UUID restaurantId, String name, String description,
            BigDecimal price, boolean availableOnlyAtRestaurant, String photoPath) {
        RestaurantEntity foundRestaurantEntity = this.restaurantGateway
                .findById(restaurantId)
                .orElseThrow(() -> new EntityNotFoundException("Restaurant"));
        ItemEntity newItem = ItemEntity
                .builder()
                .restaurant(foundRestaurantEntity)
                .name(name)
                .description(description)
                .price(price)
                .availableOnlyAtRestaurant(availableOnlyAtRestaurant)
                .photoPath(photoPath)
                .build();
        return this.itemGateway.save(newItem);
    }
}
