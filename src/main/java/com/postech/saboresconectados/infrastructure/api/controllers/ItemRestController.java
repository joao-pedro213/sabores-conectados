package com.postech.saboresconectados.infrastructure.api.controllers;

import com.postech.saboresconectados.core.controller.ItemController;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewItemDto;
import com.postech.saboresconectados.core.dtos.UpdateItemDto;
import com.postech.saboresconectados.infrastructure.api.dtos.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewItemRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateItemRequestDto;
import com.postech.saboresconectados.infrastructure.data.ItemDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.datamappers.ItemMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/item")
@AllArgsConstructor
public class ItemRestController {
    private ItemDataSourceJpa itemDataSourceJpa;
    private RestaurantDataSourceJpa restaurantDataSourceJpa;

    @PostMapping
    public ResponseEntity<ItemResponseDto> create(@Valid @RequestBody NewItemRequestDto requestDto) {
        NewItemDto newItemDto = ItemMapper.toNewItemDto(requestDto);
        ItemController itemController = ItemController.build(this.itemDataSourceJpa, this.restaurantDataSourceJpa);
        ItemDto itemDto = itemController.createItem(newItemDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(ItemMapper.toItemResponseDto(itemDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateItemRequestDto requestDto) {
        UpdateItemDto updateItemDto = ItemMapper.toUpdateItemDto(requestDto);
        ItemController itemController = ItemController.build(this.itemDataSourceJpa, this.restaurantDataSourceJpa);
        ItemDto itemDto = itemController.updateItem(id, updateItemDto);
        return ResponseEntity.status(HttpStatus.OK).body(ItemMapper.toItemResponseDto(itemDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        ItemController itemController = ItemController.build(this.itemDataSourceJpa, this.restaurantDataSourceJpa);
        itemController.deleteItemById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
