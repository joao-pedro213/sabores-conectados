package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
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
