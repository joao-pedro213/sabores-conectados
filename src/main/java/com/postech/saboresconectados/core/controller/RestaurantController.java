package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.domain.usecases.CreateRestaurantUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveItemsByRestaurantIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateRestaurantUseCase;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.ItemPresenter;
import com.postech.saboresconectados.core.presenters.RestaurantPresenter;
import com.postech.saboresconectados.core.valueobjects.DailySchedule;
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
