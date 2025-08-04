package com.postech.saboresconectados.infrastructure.data.datamappers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.postech.saboresconectados.core.dtos.DailyScheduleDto;
import com.postech.saboresconectados.core.dtos.NewRestaurantDto;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.dtos.UpdateRestaurantDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.infrastructure.api.dtos.DailyScheduleRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateRestaurantRequestDto;
import com.postech.saboresconectados.infrastructure.data.models.RestaurantModel;
import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantMapperTest {
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    // Mock DTOs and Models for testing
    private UserDto ownerUserDto;
    private UserModel ownerUserModel;
    private Map<DayOfWeek, DailyScheduleDto> businessHoursDto;
    private Map<DayOfWeek, DailyScheduleRequestDto> businessHoursRequestDto;

    @BeforeEach
    void setUp() {
        this.ownerUserDto = UserDto
                .builder()
                .id(UUID.randomUUID())
                .name("Restaurant Owner")
                .email("owner@example.com")
                .login("ownerlogin")
                .password("password123")
                .build();

        this.ownerUserModel = UserModel
                .builder()
                .id(this.ownerUserDto.getId())
                .name(this.ownerUserDto.getName())
                .email(this.ownerUserDto.getEmail())
                .login(this.ownerUserDto.getLogin())
                .password(this.ownerUserDto.getPassword())
                .build();

        this.businessHoursDto = new LinkedHashMap<>();
        this.businessHoursDto.put(DayOfWeek.MONDAY, DailyScheduleDto.builder()
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(18, 0))
                .build());
        this.businessHoursDto.put(DayOfWeek.TUESDAY, DailyScheduleDto.builder()
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(18, 0))
                .build());

        this.businessHoursRequestDto = new LinkedHashMap<>();
        this.businessHoursRequestDto.put(DayOfWeek.MONDAY, DailyScheduleRequestDto.builder()
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(18, 0))
                .build());
        this.businessHoursRequestDto.put(DayOfWeek.TUESDAY, DailyScheduleRequestDto.builder()
                .openingTime(LocalTime.of(9, 0))
                .closingTime(LocalTime.of(18, 0))
                .build());
    }

    @Test
    void shouldMapRestaurantDtoToRestaurantModel() throws JsonProcessingException {
        // Given
        RestaurantDto restaurantDto = RestaurantDto.builder()
                .id(UUID.randomUUID())
                .name("Taste of Italy")
                .address("123 Pizza St")
                .cuisineType("Italian")
                .businessHours(this.businessHoursDto)
                .owner(this.ownerUserDto)
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        RestaurantModel restaurantModel = RestaurantMapper.toRestaurantModel(restaurantDto);

        // Then
        assertThat(restaurantModel).isNotNull();
        assertThat(restaurantModel.getId()).isEqualTo(restaurantDto.getId());
        assertThat(restaurantModel.getName()).isEqualTo(restaurantDto.getName());
        assertThat(restaurantModel.getAddress()).isEqualTo(restaurantDto.getAddress());
        assertThat(restaurantModel.getCuisineType()).isEqualTo(restaurantDto.getCuisineType());
        String expectedBusinessHoursJson = this.objectMapper.writeValueAsString(this.businessHoursDto);
        assertThat(restaurantModel.getBusinessHours()).isEqualTo(expectedBusinessHoursJson);
        assertThat(restaurantModel.getOwner()).usingRecursiveComparison().isEqualTo(this.ownerUserModel);
        assertThat(restaurantModel.getLastUpdated()).isEqualTo(restaurantDto.getLastUpdated());
    }

    @Test
    void shouldMapRestaurantModelToRestaurantDto() throws JsonProcessingException {
        // Given
        String businessHoursJson = this.objectMapper.writeValueAsString(this.businessHoursDto);
        RestaurantModel restaurantModel = RestaurantModel.builder()
                .id(UUID.randomUUID())
                .name("Taste of Italy")
                .address("123 Pizza St")
                .cuisineType("Italian")
                .businessHours(businessHoursJson)
                .owner(this.ownerUserModel)
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        RestaurantDto restaurantDto = RestaurantMapper.toRestaurantDto(restaurantModel);

        // Then
        assertThat(restaurantDto).isNotNull();
        assertThat(restaurantDto.getId()).isEqualTo(restaurantModel.getId());
        assertThat(restaurantDto.getName()).isEqualTo(restaurantModel.getName());
        assertThat(restaurantDto.getAddress()).isEqualTo(restaurantModel.getAddress());
        assertThat(restaurantDto.getCuisineType()).isEqualTo(restaurantModel.getCuisineType());
        assertThat(restaurantDto.getBusinessHours()).usingRecursiveComparison().isEqualTo(this.businessHoursDto);
        assertThat(restaurantDto.getOwner()).usingRecursiveComparison().isEqualTo(this.ownerUserDto);
        assertThat(restaurantDto.getLastUpdated()).isEqualTo(restaurantModel.getLastUpdated());
    }

    @Test
    void shouldMapNewRestaurantRequestDtoToNewRestaurantDto() {
        // Given
        UUID ownerId = UUID.randomUUID();
        NewRestaurantRequestDto newRestaurantRequestDto = NewRestaurantRequestDto.builder()
                .name("Burger Palace")
                .address("456 Grill Ave")
                .cuisineType("American")
                .businessHours(businessHoursRequestDto)
                .ownerId(ownerId.toString())
                .build();

        // When
        NewRestaurantDto newRestaurantDto = RestaurantMapper.toNewRestaurantDto(newRestaurantRequestDto);

        // Then
        assertThat(newRestaurantDto).isNotNull();
        assertThat(newRestaurantDto.getName()).isEqualTo(newRestaurantRequestDto.getName());
        assertThat(newRestaurantDto.getAddress()).isEqualTo(newRestaurantRequestDto.getAddress());
        assertThat(newRestaurantDto.getCuisineType()).isEqualTo(newRestaurantRequestDto.getCuisineType());
        assertThat(newRestaurantDto.getOwnerId()).isEqualTo(ownerId);
        assertThat(newRestaurantDto.getBusinessHours()).isNotNull();
        assertThat(newRestaurantDto.getBusinessHours().size()).isEqualTo(businessHoursRequestDto.size());
        assertThat(newRestaurantDto.getBusinessHours().get(DayOfWeek.MONDAY))
                .usingRecursiveComparison().isEqualTo(businessHoursDto.get(DayOfWeek.MONDAY));
    }

    @Test
    void shouldMapUpdateRestaurantRequestDtoToUpdateRestaurantDto() {
        // Given
        UpdateRestaurantRequestDto updateRestaurantRequestDto = UpdateRestaurantRequestDto.builder()
                .address("789 New St")
                .businessHours(this.businessHoursRequestDto)
                .build();

        // When
        UpdateRestaurantDto updateRestaurantDto = RestaurantMapper.toUpdateRestaurantDto(updateRestaurantRequestDto);

        // Then
        assertThat(updateRestaurantDto).isNotNull();
        assertThat(updateRestaurantDto.getAddress()).isEqualTo(updateRestaurantRequestDto.getAddress());
        assertThat(updateRestaurantDto.getBusinessHours()).isNotNull();
        assertThat(updateRestaurantDto.getBusinessHours().size()).isEqualTo(this.businessHoursRequestDto.size());
        assertThat(updateRestaurantDto.getBusinessHours().get(DayOfWeek.TUESDAY))
                .usingRecursiveComparison().isEqualTo(this.businessHoursDto.get(DayOfWeek.TUESDAY));
    }
}
