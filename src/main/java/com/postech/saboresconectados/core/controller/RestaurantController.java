package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.CuisineType;
import com.postech.saboresconectados.core.domain.usecases.CreateRestaurantUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateRestaurantUseCase;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.RestaurantPresenter;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@AllArgsConstructor
public class RestaurantController {
    private RestaurantDataSource restaurantDataSource;
    private UserDataSource userDataSource;

    public static RestaurantController create(
            RestaurantDataSource restaurantDataSource,
            UserDataSource userDataSource) {
        return new RestaurantController(restaurantDataSource, userDataSource);
    }

    public RestaurantOutputDto createRestaurant(NewRestaurantDto newRestaurantDto) {
        RestaurantGateway restaurantGateway = RestaurantGateway.create(this.restaurantDataSource);
        CreateRestaurantUseCase useCase = CreateRestaurantUseCase.create(restaurantGateway);
        Restaurant restaurant = useCase.execute(this.toDomain(newRestaurantDto));
        RestaurantPresenter presenter = RestaurantPresenter.create();
        return presenter.toDto(restaurant);
    }

    public RestaurantOutputDto retrieveRestaurantById(UUID id) {
        RestaurantGateway restaurantGateway = RestaurantGateway.create(this.restaurantDataSource);
        RetrieveRestaurantByIdUseCase useCase = RetrieveRestaurantByIdUseCase.create(restaurantGateway);
        Restaurant foundRestaurant = useCase.execute(id);
        RestaurantPresenter presenter = RestaurantPresenter.create();
        return presenter.toDto(foundRestaurant);
    }

    public RestaurantOutputDto updateRestaurant(UUID id, UpdateRestaurantDto updateRestaurantDto) {
        RestaurantGateway restaurantGateway = RestaurantGateway.create(this.restaurantDataSource);
        UpdateRestaurantUseCase useCase = UpdateRestaurantUseCase.create(restaurantGateway);
        Restaurant updatedRestaurant = useCase
                .execute(
                        id,
                        updateRestaurantDto.getAddress(),
                        this.toBusinessHours(updateRestaurantDto.getBusinessHours()));
        RestaurantPresenter presenter = RestaurantPresenter.create();
        return presenter.toDto(updatedRestaurant);
    }

    public void deleteRestaurantById(UUID id) {
        RestaurantGateway restaurantGateway = RestaurantGateway.create(this.restaurantDataSource);
        DeleteRestaurantByIdUseCase useCase = DeleteRestaurantByIdUseCase.create(restaurantGateway);
        useCase.execute(id);
    }

    private Restaurant toDomain(NewRestaurantDto newRestaurantDto) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        RetrieveUserByIdUseCase retrieveUserByIdUseCase = RetrieveUserByIdUseCase.create(userGateway);
        User owner = retrieveUserByIdUseCase.execute(newRestaurantDto.getOwnerId());
        return Restaurant
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
