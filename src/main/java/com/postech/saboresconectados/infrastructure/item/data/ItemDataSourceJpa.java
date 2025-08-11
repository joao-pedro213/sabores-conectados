package com.postech.saboresconectados.infrastructure.item.data;

import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.datasource.ItemDataSource;
import com.postech.saboresconectados.infrastructure.item.data.mapper.ItemMapper;
import com.postech.saboresconectados.infrastructure.item.data.model.ItemModel;
import com.postech.saboresconectados.infrastructure.item.data.repository.ItemRepository;
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
