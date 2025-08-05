package com.postech.saboresconectados.core.interfaces;


import com.postech.saboresconectados.core.dtos.ItemDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemDataSource {
    ItemDto save(ItemDto itemDto);
    Optional<ItemDto> findById(UUID id);
    List<ItemDto> findAllByRestaurantId(UUID restaurantId);
    void deleteById(UUID id);
}
