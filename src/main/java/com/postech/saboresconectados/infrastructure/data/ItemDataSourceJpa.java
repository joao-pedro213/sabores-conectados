package com.postech.saboresconectados.infrastructure.data;

import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.infrastructure.data.datamappers.ItemMapper;
import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import com.postech.saboresconectados.infrastructure.data.repositories.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

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
}
