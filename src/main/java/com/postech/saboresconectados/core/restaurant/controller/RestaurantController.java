package com.postech.saboresconectados.core.restaurant.controller;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.restaurant.domain.entity.enumerator.CuisineType;
import com.postech.saboresconectados.core.restaurant.domain.usecase.CreateRestaurantUseCase;
import com.postech.saboresconectados.core.restaurant.domain.usecase.DeleteRestaurantByIdUseCase;
import com.postech.saboresconectados.core.restaurant.domain.usecase.RetrieveItemsByRestaurantIdUseCase;
import com.postech.saboresconectados.core.restaurant.domain.usecase.RetrieveRestaurantByIdUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.restaurant.domain.usecase.UpdateRestaurantUseCase;
import com.postech.saboresconectados.core.restaurant.dto.DailyScheduleDto;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.restaurant.dto.NewRestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.restaurant.dto.UpdateRestaurantDto;
import com.postech.saboresconectados.core.item.gateway.ItemGateway;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import com.postech.saboresconectados.core.item.datasource.ItemDataSource;
import com.postech.saboresconectados.core.restaurant.datasource.RestaurantDataSource;
import com.postech.saboresconectados.core.user.datasource.UserDataSource;
import com.postech.saboresconectados.core.item.presenter.ItemPresenter;
import com.postech.saboresconectados.core.restaurant.presenter.RestaurantPresenter;
import com.postech.saboresconectados.core.restaurant.valueobject.DailySchedule;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class RestaurantController {
    private RestaurantDataSource restaurantDataSource;
    private UserDataSource userDataSource;
    private ItemDataSource itemDataSource;

    public static RestaurantController build(
            RestaurantDataSource restaurantDataSource,
            UserDataSource userDataSource,
            ItemDataSource itemDataSource) {
        return new RestaurantController(restaurantDataSource, userDataSource, itemDataSource);
    }

    public RestaurantDto createRestaurant(NewRestaurantDto newRestaurantDto) {
        RestaurantGateway restaurantGateway = RestaurantGateway.build(this.restaurantDataSource);
        CreateRestaurantUseCase useCase = CreateRestaurantUseCase.build(restaurantGateway);
        RestaurantEntity createdRestaurant = useCase.execute(this.toDomain(newRestaurantDto));
        RestaurantPresenter presenter = RestaurantPresenter.build();
        return presenter.toDto(createdRestaurant);
    }

    public RestaurantDto retrieveRestaurantById(UUID id) {
        RestaurantGateway restaurantGateway = RestaurantGateway.build(this.restaurantDataSource);
        RetrieveRestaurantByIdUseCase useCase = RetrieveRestaurantByIdUseCase.build(restaurantGateway);
        RestaurantEntity foundRestaurant = useCase.execute(id);
        RestaurantPresenter presenter = RestaurantPresenter.build();
        return presenter.toDto(foundRestaurant);
    }

    public RestaurantDto updateRestaurant(UUID id, UpdateRestaurantDto updateRestaurantDto) {
        RestaurantGateway restaurantGateway = RestaurantGateway.build(this.restaurantDataSource);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.build(restaurantGateway);
        RestaurantEntity updatedRestaurant = useCase
                .execute(
                        id,
                        updateRestaurantDto.getAddress(),
                        this.toBusinessHours(updateRestaurantDto.getBusinessHours()));
        RestaurantPresenter presenter = RestaurantPresenter.build();
        return presenter.toDto(updatedRestaurant);
    }

    public void deleteRestaurantById(UUID id) {
        RestaurantGateway restaurantGateway = RestaurantGateway.build(this.restaurantDataSource);
        DeleteRestaurantByIdUseCase useCase = DeleteRestaurantByIdUseCase.build(restaurantGateway);
        useCase.execute(id);
    }

    public List<ItemDto> retrieveItemsByRestaurantId(UUID id) {
        ItemGateway itemGateway = ItemGateway.build(this.itemDataSource);
        RetrieveItemsByRestaurantIdUseCase useCase = RetrieveItemsByRestaurantIdUseCase.build(itemGateway);
        List<ItemEntity> foundItems = useCase.execute(id);
        ItemPresenter presenter = ItemPresenter.build();
        return foundItems.stream().map(presenter::toDto).toList();
    }

    private RestaurantEntity toDomain(NewRestaurantDto newRestaurantDto) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        RetrieveUserByIdUseCase retrieveUserByIdUseCase = RetrieveUserByIdUseCase.build(userGateway);
        UserEntity owner = retrieveUserByIdUseCase.execute(newRestaurantDto.getOwnerId());
        return RestaurantEntity
                .builder()
                .name(newRestaurantDto.getName())
                .address(newRestaurantDto.getAddress())
                .cuisineType(CuisineType.fromValue(newRestaurantDto.getCuisineType()))
                .businessHours(this.toBusinessHours(newRestaurantDto.getBusinessHours()))
                .owner(owner)
                .build();
    }

    private Map<DayOfWeek, DailySchedule> toBusinessHours(Map<DayOfWeek, DailyScheduleDto> businessHoursDto) {
        Map<DayOfWeek, DailySchedule> businessHours = new LinkedHashMap<>();
        businessHoursDto
                .forEach(((dayOfWeek, dailySchedule) ->
                        businessHours.put(
                                dayOfWeek,
                                DailySchedule
                                        .builder()
                                        .openingTime(dailySchedule.getOpeningTime())
                                        .closingTime(dailySchedule.getClosingTime())
                                        .build())));
        return businessHours;
    }
}
