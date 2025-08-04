package com.postech.saboresconectados.core.interfaces;


import com.postech.saboresconectados.core.dtos.ItemDto;

public interface ItemDataSource {
    ItemDto save(ItemDto itemDto);
}
