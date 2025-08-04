package com.postech.saboresconectados.core.controllers;

import com.postech.saboresconectados.core.controller.RestaurantController;
import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.usecases.CreateRestaurantUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveRestaurantByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateRestaurantUseCase;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantOutputDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.RestaurantPresenter;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {
    @Mock
    private RestaurantDataSource mockRestaurantDataSource;

    @Mock
    private RestaurantGateway mockRestaurantGateway;

    private MockedStatic<RestaurantGateway> mockedStaticRestaurantGateway;

    @Mock
    private UserDataSource mockUserDataSource;

    @Mock
    private UserGateway mockUserGateway;

    private MockedStatic<UserGateway> mockedStaticUserGateway;

    @Mock
    private RestaurantPresenter mockRestaurantPresenter;

    private MockedStatic<RestaurantPresenter> mockedStaticRestaurantPresenter;

    @Mock
    private RetrieveUserByIdUseCase mockRetrieveUserByIdUseCase;

    private MockedStatic<RetrieveUserByIdUseCase> mockedStaticRetrieveUserByIdUseCase;

    @Mock
    private CreateRestaurantUseCase mockCreateRestaurantUseCase;

    private MockedStatic<CreateRestaurantUseCase> mockedStaticCreateRestaurantUseCase;

    @Mock
    private RetrieveRestaurantByIdUseCase mockRetrieveRestaurantByIdUseCase;

    private MockedStatic<RetrieveRestaurantByIdUseCase> mockedStaticRetrieveRestaurantByIdUseCase;

    @Mock
    private UpdateRestaurantUseCase mockUpdateRestaurantUseCase;

    private MockedStatic<UpdateRestaurantUseCase> mockedStaticUpdateRestaurantUseCase;

    @Mock
    private DeleteRestaurantByIdUseCase mockDeleteRestaurantByIdUseCase;

    private MockedStatic<DeleteRestaurantByIdUseCase> mockedStaticDeleteRestaurantByIdUseCase;

    @InjectMocks
    private RestaurantController controller;

    private Map<String, Object> restaurantSampleData;

    private final RestaurantObjectMother restaurantObjectMother = new RestaurantObjectMother();

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    private static final LocalDateTime RESTAURANT_LAST_UPDATED = LocalDateTime.now();

    private static final UUID OWNER_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticRestaurantGateway = mockStatic(RestaurantGateway.class);
        this.mockedStaticRestaurantGateway
                .when(() -> RestaurantGateway.create(this.mockRestaurantDataSource))
                .thenReturn(this.mockRestaurantGateway);
        this.mockedStaticUserGateway = mockStatic(UserGateway.class);
        this.mockedStaticUserGateway
                .when(() -> UserGateway.create(this.mockUserDataSource))
                .thenReturn(this.mockUserGateway);
        this.mockedStaticRestaurantPresenter = mockStatic(RestaurantPresenter.class);
        this.mockedStaticRestaurantPresenter.when(RestaurantPresenter::create).thenReturn(this.mockRestaurantPresenter);
        this.mockedStaticRetrieveUserByIdUseCase = mockStatic(RetrieveUserByIdUseCase.class);
        this.mockedStaticRetrieveUserByIdUseCase
                .when(() -> RetrieveUserByIdUseCase.create(this.mockUserGateway))
                .thenReturn(this.mockRetrieveUserByIdUseCase);
        this.mockedStaticCreateRestaurantUseCase = mockStatic(CreateRestaurantUseCase.class);
        this.mockedStaticCreateRestaurantUseCase
                .when(() -> CreateRestaurantUseCase.create(this.mockRestaurantGateway))
                .thenReturn(this.mockCreateRestaurantUseCase);
        this.mockedStaticRetrieveRestaurantByIdUseCase = mockStatic(RetrieveRestaurantByIdUseCase.class);
        this.mockedStaticRetrieveRestaurantByIdUseCase
                .when(() -> RetrieveRestaurantByIdUseCase.create(this.mockRestaurantGateway))
                .thenReturn(this.mockRetrieveRestaurantByIdUseCase);
        this.mockedStaticUpdateRestaurantUseCase = mockStatic(UpdateRestaurantUseCase.class);
        this.mockedStaticUpdateRestaurantUseCase
                .when(() -> UpdateRestaurantUseCase.create(this.mockRestaurantGateway))
                .thenReturn(this.mockUpdateRestaurantUseCase);
        this.mockedStaticDeleteRestaurantByIdUseCase = mockStatic(DeleteRestaurantByIdUseCase.class);
        this.mockedStaticDeleteRestaurantByIdUseCase
                .when(() -> DeleteRestaurantByIdUseCase.create(this.mockRestaurantGateway))
                .thenReturn(this.mockDeleteRestaurantByIdUseCase);
        this.restaurantSampleData = new LinkedHashMap<>();
        this.restaurantSampleData.put("id", RESTAURANT_ID.toString());
        this.restaurantSampleData.put("name", "The Gemini Grill");
        this.restaurantSampleData.put("address", "123 AI Avenue, Tech City");
        this.restaurantSampleData.put("cuisineType", "Japanese");
        this.restaurantSampleData.put("ownerId", OWNER_ID);
        this.restaurantSampleData.put("owner", getOwnerSampleData());
        this.restaurantSampleData.put("businessHours", getBusinessHoursSampleData());
        this.restaurantSampleData.put("lastUpdated", RESTAURANT_LAST_UPDATED);
    }

    private static Map<String, Object> getOwnerSampleData() {
        Map<String, Object> ownerSampleData = new LinkedHashMap<>();
        ownerSampleData.put("id", OWNER_ID.toString());
        ownerSampleData.put("name", "Marcos");
        ownerSampleData.put("userType", UserType.RESTAURANT_OWNER.getValue());
        ownerSampleData.put("email", "marcos@mail.com");
        ownerSampleData.put("login", "marcos635");
        ownerSampleData.put("password", "09231s121!G");
        ownerSampleData.put("address", "82495 Xavier Keys, Emersonburgh, KS 65336-8213");
        ownerSampleData.put("lastUpdated", "2025-08-02T21:38:21.635599007");
        return ownerSampleData;
    }

    private static Map<String, Object> getBusinessHoursSampleData() {
        Map<String, Object> businessHoursData = new LinkedHashMap<>();
        Map<String, String> fridaySchedule = new LinkedHashMap<>();
        fridaySchedule.put("openingTime", "10:00");
        fridaySchedule.put("closingTime", "23:00");
        businessHoursData.put("FRIDAY", fridaySchedule);
        Map<String, String> saturdaySchedule = new LinkedHashMap<>();
        saturdaySchedule.put("openingTime", "11:00");
        saturdaySchedule.put("closingTime", "23:00");
        businessHoursData.put("SATURDAY", saturdaySchedule);
        Map<String, String> sundaySchedule = new LinkedHashMap<>();
        sundaySchedule.put("openingTime", "11:00");
        sundaySchedule.put("closingTime", "20:00");
        businessHoursData.put("SUNDAY", sundaySchedule);
        return businessHoursData;
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticRestaurantGateway != null) {
            this.mockedStaticRestaurantGateway.close();
        }
        if (this.mockedStaticUserGateway != null) {
            this.mockedStaticUserGateway.close();
        }
        if (this.mockedStaticRestaurantPresenter != null) {
            this.mockedStaticRestaurantPresenter.close();
        }
        if (this.mockedStaticRetrieveUserByIdUseCase != null) {
            this.mockedStaticRetrieveUserByIdUseCase.close();
        }
        if (this.mockedStaticCreateRestaurantUseCase != null) {
            this.mockedStaticCreateRestaurantUseCase.close();
        }
        if (this.mockedStaticRetrieveRestaurantByIdUseCase != null) {
            this.mockedStaticRetrieveRestaurantByIdUseCase.close();
        }
        if (this.mockedStaticUpdateRestaurantUseCase != null) {
            this.mockedStaticUpdateRestaurantUseCase.close();
        }
        if (this.mockedStaticDeleteRestaurantByIdUseCase != null) {
            this.mockedStaticDeleteRestaurantByIdUseCase.close();
        }
    }

    @Test
    void shouldCreateRestaurant() {
        // Given
        final NewRestaurantDto newRestaurantDto = this.restaurantObjectMother
                .createSampleNewRestaurantDto(this.restaurantSampleData);
        final Restaurant createdRestaurant = this.restaurantObjectMother
                .createSampleRestaurant(this.restaurantSampleData);
        final RestaurantOutputDto createdRestaurantOutputDto = RestaurantOutputDto.builder().build();
        final User foundUser = createdRestaurant.getOwner().toBuilder().build();
        when(this.mockRetrieveUserByIdUseCase.execute(newRestaurantDto.getOwnerId())).thenReturn(foundUser);
        this.mockedStaticCreateRestaurantUseCase
                .when(() -> CreateRestaurantUseCase.create(this.mockRestaurantGateway))
                .thenReturn(this.mockCreateRestaurantUseCase);
        when(this.mockCreateRestaurantUseCase.execute(any(Restaurant.class))).thenReturn(createdRestaurant);
        when(this.mockRestaurantPresenter.toDto(createdRestaurant)).thenReturn(createdRestaurantOutputDto);

        // When
        final RestaurantOutputDto restaurantOutputDto = this.controller.createRestaurant(newRestaurantDto);

        // Then
        final ArgumentCaptor<Restaurant> argument = ArgumentCaptor.forClass(Restaurant.class);
        this.mockedStaticRestaurantGateway
                .verify(() -> RestaurantGateway.create(this.mockRestaurantDataSource), times(1));
        this.mockedStaticCreateRestaurantUseCase
                .verify(() -> CreateRestaurantUseCase.create(this.mockRestaurantGateway), times(1));
        this.mockedStaticUserGateway.verify(() -> UserGateway.create(this.mockUserDataSource), times(1));
        this.mockedStaticRetrieveUserByIdUseCase
                .verify(() -> RetrieveUserByIdUseCase.create(this.mockUserGateway), times(1));
        verify(this.mockRetrieveUserByIdUseCase, times(1)).execute(newRestaurantDto.getOwnerId());
        verify(this.mockCreateRestaurantUseCase, times(1)).execute(argument.capture());
        this.mockedStaticRestaurantPresenter.verify(RestaurantPresenter::create, times(1));
        verify(this.mockRestaurantPresenter, times(1)).toDto(createdRestaurant);
        final Restaurant capturedRestaurant = argument.getValue();
        final Restaurant expectedRestaurant = createdRestaurant.toBuilder().id(null).lastUpdated(null).build();
        assertThat(capturedRestaurant).usingRecursiveComparison().isEqualTo(expectedRestaurant);
        assertThat(restaurantOutputDto).isNotNull().isEqualTo(createdRestaurantOutputDto);
    }

    @Test
    void shouldRetrieveRestaurantById() {
        // Given
        final Restaurant foundRestaurant = this.restaurantObjectMother
                .createSampleRestaurant(this.restaurantSampleData);
        when(this.mockRetrieveRestaurantByIdUseCase.execute(RESTAURANT_ID)).thenReturn(foundRestaurant);
        final RestaurantOutputDto foundRestaurantOutputDto = RestaurantOutputDto.builder().build();
        when(this.mockRestaurantPresenter.toDto(foundRestaurant)).thenReturn(foundRestaurantOutputDto);

        // When
        final RestaurantOutputDto restaurantOutputDto = this.controller.retrieveRestaurantById(RESTAURANT_ID);

        // Then
        this.mockedStaticRestaurantGateway
                .verify(() -> RestaurantGateway.create(this.mockRestaurantDataSource), times(1));
        this.mockedStaticRetrieveRestaurantByIdUseCase
                .verify(() -> RetrieveRestaurantByIdUseCase.create(this.mockRestaurantGateway), times(1));
        verify(this.mockRetrieveRestaurantByIdUseCase, times(1)).execute(RESTAURANT_ID);
        this.mockedStaticRestaurantPresenter.verify(RestaurantPresenter::create, times(1));
        verify(this.mockRestaurantPresenter, times(1)).toDto(foundRestaurant);
        assertThat(restaurantOutputDto).isNotNull().isEqualTo(foundRestaurantOutputDto);
    }

    @Test
    void shouldUpdateRestaurant() {
        // Given
        final UpdateRestaurantDto updateRestaurantDto = this.restaurantObjectMother
                .createSampleUpdateRestaurantDto(this.restaurantSampleData);
        final Restaurant updatedRestaurant = this.restaurantObjectMother
                .createSampleRestaurant(this.restaurantSampleData);
        when(
                this.mockUpdateRestaurantUseCase
                        .execute(
                                eq(RESTAURANT_ID),
                                eq(updatedRestaurant.getAddress()),
                                any(LinkedHashMap.class)))
                .thenReturn(updatedRestaurant);
        final RestaurantOutputDto updatedRestaurantOutputDto = RestaurantOutputDto.builder().build();
        when(this.mockRestaurantPresenter.toDto(updatedRestaurant)).thenReturn(updatedRestaurantOutputDto);

        // When
        final RestaurantOutputDto restaurantOutputDto = this.controller
                .updateRestaurant(RESTAURANT_ID, updateRestaurantDto);

        // Then
        this.mockedStaticRestaurantGateway
                .verify(() -> RestaurantGateway.create(this.mockRestaurantDataSource), times(1));
        this.mockedStaticUpdateRestaurantUseCase
                .verify(() -> UpdateRestaurantUseCase.create(this.mockRestaurantGateway), times(1));
        verify(this.mockUpdateRestaurantUseCase, times(1))
                .execute(
                        eq(RESTAURANT_ID),
                        eq(updateRestaurantDto.getAddress()),
                        any(LinkedHashMap.class));
        this.mockedStaticRestaurantPresenter.verify(RestaurantPresenter::create, times(1));
        verify(this.mockRestaurantPresenter, times(1)).toDto(updatedRestaurant);
        assertThat(restaurantOutputDto).isNotNull().isEqualTo(updatedRestaurantOutputDto);
    }

    @Test
    void shouldDeleteRestaurantById() {
        // When
        this.controller.deleteRestaurantById(RESTAURANT_ID);

        // Then
        this.mockedStaticRestaurantGateway
                .verify(() -> RestaurantGateway.create(this.mockRestaurantDataSource), times(1));
        this.mockedStaticDeleteRestaurantByIdUseCase
                .verify(() -> DeleteRestaurantByIdUseCase.create(this.mockRestaurantGateway), times(1));
        verify(this.mockDeleteRestaurantByIdUseCase, times(1)).execute(RESTAURANT_ID);
    }
}
