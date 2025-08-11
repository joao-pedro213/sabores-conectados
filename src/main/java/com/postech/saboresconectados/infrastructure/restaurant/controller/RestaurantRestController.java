package com.postech.saboresconectados.infrastructure.restaurant.controller;

import com.postech.saboresconectados.core.restaurant.controller.RestaurantController;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.restaurant.dto.NewRestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.UpdateRestaurantDto;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.RestaurantResponseDto;
import com.postech.saboresconectados.infrastructure.restaurant.dto.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.item.data.ItemDataSourceJpa;
import com.postech.saboresconectados.infrastructure.restaurant.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.user.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.item.data.mapper.ItemMapper;
import com.postech.saboresconectados.infrastructure.restaurant.data.mapper.RestaurantMapper;
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
