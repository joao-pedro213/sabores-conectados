package com.postech.saboresconectados.infrastructure.api.controllers;

import com.postech.saboresconectados.core.controller.RestaurantController;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.infrastructure.api.dtos.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.RestaurantResponseDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.data.ItemDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.datamappers.ItemMapper;
import com.postech.saboresconectados.infrastructure.data.datamappers.RestaurantMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantRestController {
    private RestaurantDataSourceJpa restaurantDataSourceJpa;
    private UserDataSourceJpa userDataSourceJpa;
    private ItemDataSourceJpa itemDataSourceJpa;

    @PostMapping
    public ResponseEntity<RestaurantResponseDto> create(@Valid @RequestBody NewRestaurantRequestDto requestDto) {
        NewRestaurantDto newRestaurantDto = RestaurantMapper.toNewRestaurantDto(requestDto);
        RestaurantController restaurantController = RestaurantController
                .build(
                        this.restaurantDataSourceJpa,
                        this.userDataSourceJpa,
                        this.itemDataSourceJpa);
        RestaurantDto restaurantDto = restaurantController.createRestaurant(newRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(RestaurantMapper.toRestaurantResponseDto(restaurantDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> retrieve(@PathVariable UUID id) {
        RestaurantController restaurantController = RestaurantController
                .build(
                        this.restaurantDataSourceJpa,
                        this.userDataSourceJpa,
                        this.itemDataSourceJpa);
        RestaurantDto restaurantDto = restaurantController.retrieveRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(RestaurantMapper.toRestaurantResponseDto(restaurantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantResponseDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRestaurantRequestDto requestDto) {
        UpdateRestaurantDto updateRestaurantDto = RestaurantMapper.toUpdateRestaurantDto(requestDto);
        RestaurantController restaurantController = RestaurantController
                .build(
                        this.restaurantDataSourceJpa,
                        this.userDataSourceJpa,
                        this.itemDataSourceJpa);
        RestaurantDto restaurantDto = restaurantController.updateRestaurant(id, updateRestaurantDto);
        return ResponseEntity.status(HttpStatus.OK).body(RestaurantMapper.toRestaurantResponseDto(restaurantDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        RestaurantController restaurantController = RestaurantController
                .build(
                        this.restaurantDataSourceJpa,
                        this.userDataSourceJpa,
                        this.itemDataSourceJpa);
        restaurantController.deleteRestaurantById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}/menu")
    public ResponseEntity<List<ItemResponseDto>> retrieveMenu(@PathVariable UUID id) {
        RestaurantController restaurantController = RestaurantController
                .build(
                        this.restaurantDataSourceJpa,
                        this.userDataSourceJpa,
                        this.itemDataSourceJpa);
        List<ItemDto> items = restaurantController.retrieveItemsByRestaurantId(id);
        return ResponseEntity.status(HttpStatus.OK).body(items.stream().map(ItemMapper::toItemResponseDto).toList());
    }
}
