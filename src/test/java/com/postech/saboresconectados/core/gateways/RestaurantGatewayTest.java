package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.core.interfaces.RestaurantDataSource;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantGatewayTest {
    @Mock
    private RestaurantDataSource dataSource;

    @InjectMocks
    private RestaurantGateway gateway;

    private Map<String, Object> restaurantSampleData;

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    private static final LocalDateTime RESTAURANT_LAST_UPDATED = LocalDateTime.now();

    private static final UUID OWNER_ID = UUID.randomUUID();

    private static final LocalDateTime OWNER_LAST_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.restaurantSampleData = new LinkedHashMap<>();
        this.restaurantSampleData.put("id", RESTAURANT_ID.toString());
        this.restaurantSampleData.put("name", "The Gemini Grill");
        this.restaurantSampleData.put("address", "123 AI Avenue, Tech City");
        this.restaurantSampleData.put("cuisineType", "Japanese");
        this.restaurantSampleData.put("businessHours", getBusinessHoursSampleData());
        this.restaurantSampleData.put("ownerId", OWNER_ID.toString());
        this.restaurantSampleData.put("owner", getOwnerSampleData());
        this.restaurantSampleData.put("lastUpdated", RESTAURANT_LAST_UPDATED.toString());
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
        ownerSampleData.put("lastUpdated", OWNER_LAST_UPDATED.toString());
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

    @Test
    void shouldSaveRestaurant() {
        // Given
        final RestaurantEntity restaurantToSave = RestaurantObjectMother
                .buildRestaurantEntity(this.restaurantSampleData);
        final RestaurantDto savedRestaurantDto = RestaurantObjectMother
                .buildRestaurantDto(this.restaurantSampleData);
        when(this.dataSource.save(any(RestaurantDto.class))).thenReturn(savedRestaurantDto);

        // When
        final RestaurantEntity savedRestaurant = this.gateway.save(restaurantToSave);

        // Then
        final ArgumentCaptor<RestaurantDto> argument = ArgumentCaptor.forClass(RestaurantDto.class);
        verify(this.dataSource, times(1)).save(argument.capture());
        final RestaurantDto capturedRestaurantDto = argument.getValue();
        final RestaurantDto expectedRestaurantDto = savedRestaurantDto.toBuilder().build();
        assertThat(capturedRestaurantDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedRestaurantDto);
        assertThat(savedRestaurant).isNotNull();
        final RestaurantEntity expectedUpdatedRestaurant = restaurantToSave.toBuilder().build();
        assertThat(savedRestaurant).usingRecursiveComparison().isEqualTo(expectedUpdatedRestaurant);
    }

    @Test
    void shouldFindRestaurantById() {
        // Given
        final RestaurantDto foundRestaurantDto = RestaurantObjectMother
                .buildRestaurantDto(this.restaurantSampleData);
        when(this.dataSource.findById(RESTAURANT_ID)).thenReturn(Optional.of(foundRestaurantDto));

        // When
        Optional<RestaurantEntity> foundRestaurant = this.gateway.findById(RESTAURANT_ID);

        // Then
        verify(this.dataSource, times(1)).findById(RESTAURANT_ID);
        assertThat(foundRestaurant).isPresent();
        final RestaurantEntity expectedFoundRestaurant = RestaurantObjectMother
                .buildRestaurantEntity(this.restaurantSampleData);
        assertThat(foundRestaurant.get()).usingRecursiveComparison().isEqualTo(expectedFoundRestaurant);
    }

    @Test
    void shouldFindRestaurantByName() {
        // Given
        final String name = this.restaurantSampleData.get("name").toString();
        final RestaurantDto foundRestaurantDto = RestaurantObjectMother
                .buildRestaurantDto(this.restaurantSampleData);
        when(this.dataSource.findByName(name)).thenReturn(Optional.of(foundRestaurantDto));

        // When
        Optional<RestaurantEntity> foundRestaurant = this.gateway.findByName(name);

        // Then
        verify(this.dataSource, times(1)).findByName(name);
        assertThat(foundRestaurant).isPresent();
        final RestaurantEntity expectedFoundRestaurant = RestaurantObjectMother
                .buildRestaurantEntity(this.restaurantSampleData);
        assertThat(foundRestaurant.get()).usingRecursiveComparison().isEqualTo(expectedFoundRestaurant);
    }

    @Test
    void shouldDeleteRestaurantById() {
        // When
        this.gateway.deleteById(RESTAURANT_ID);

        // Then
        verify(this.dataSource, times(1)).deleteById(RESTAURANT_ID);
    }
}
