package com.postech.saboresconectados.infrastructure.api.controllers;

import com.postech.saboresconectados.core.controller.RestaurantController;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.data.RestaurantDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.UserDataSourceJpa;
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

import java.util.UUID;

@RestController
@RequestMapping("/restaurant")
@AllArgsConstructor
public class RestaurantRestController {
    private RestaurantDataSourceJpa restaurantDataSourceJpa;
    private UserDataSourceJpa userDataSourceJpa;

    @PostMapping
    public ResponseEntity<RestaurantOutputDto> create(@Valid @RequestBody NewRestaurantRequestDto requestDto) {
        NewRestaurantDto newRestaurantDto = RestaurantMapper.toNewRestaurantDto(requestDto);
        RestaurantController restaurantController = RestaurantController
                .create(this.restaurantDataSourceJpa, this.userDataSourceJpa);
        RestaurantOutputDto restaurantOutputDto = restaurantController.createRestaurant(newRestaurantDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantOutputDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestaurantOutputDto> retrieve(@PathVariable UUID id) {
        RestaurantController restaurantController = RestaurantController
                .create(this.restaurantDataSourceJpa, this.userDataSourceJpa);
        RestaurantOutputDto restaurantOutputDto = restaurantController.retrieveRestaurantById(id);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOutputDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestaurantOutputDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateRestaurantRequestDto requestDto) {
        UpdateRestaurantDto updateRestaurantDto = RestaurantMapper.toUpdateRestaurantDto(requestDto);
        RestaurantController restaurantController = RestaurantController
                .create(this.restaurantDataSourceJpa, this.userDataSourceJpa);
        RestaurantOutputDto restaurantOutputDto = restaurantController.updateRestaurant(id, updateRestaurantDto);
        return ResponseEntity.status(HttpStatus.OK).body(restaurantOutputDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        RestaurantController restaurantController = RestaurantController
                .create(this.restaurantDataSourceJpa, this.userDataSourceJpa);
        restaurantController.deleteRestaurantById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
