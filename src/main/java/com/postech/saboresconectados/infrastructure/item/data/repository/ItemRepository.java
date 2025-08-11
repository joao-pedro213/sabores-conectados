package com.postech.saboresconectados.infrastructure.item.data.repository;

import com.postech.saboresconectados.infrastructure.item.data.model.ItemModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends JpaRepository<ItemModel, UUID> {
    List<ItemModel> findAllByRestaurantId(UUID restaurantId);
}
