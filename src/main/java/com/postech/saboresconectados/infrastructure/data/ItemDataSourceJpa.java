package com.postech.saboresconectados.infrastructure.data;

import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.infrastructure.data.datamappers.ItemMapper;
import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import com.postech.saboresconectados.infrastructure.data.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ItemDataSourceJpa implements ItemDataSource {
    private final ItemRepository repository;

    @Override
    public ItemDto save(ItemDto itemDto) {
        ItemModel itemToSave = ItemMapper.toItemModel(itemDto);
        ItemModel savedItem = this.repository.save(itemToSave);
        return ItemMapper.toItemDto(savedItem);
    }

    @Override
    public List<ItemDto> findAllByRestaurantId(UUID restaurantId) {
        List<ItemModel> foundItems = this.repository.findAllByRestaurantId(restaurantId);
        return foundItems.stream().map(ItemMapper::toItemDto).toList();
    }

    @Override
    public Optional<ItemDto> findById(UUID id) {
        Optional<ItemModel> foundItem = this.repository.findById(id);
        return foundItem.map(ItemMapper::toItemDto);
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }
}
