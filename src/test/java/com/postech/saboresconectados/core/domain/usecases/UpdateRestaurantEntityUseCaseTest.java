package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.valueobjects.DailySchedule;
import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UpdateRestaurantEntityUseCaseTest {
    @Mock
    private RestaurantGateway mockRestaurantGateway;

    @InjectMocks
    private UpdateRestaurantUseCase useCase;

    private Map<String, Object> restaurantSampleData;

    private final RestaurantObjectMother restaurantObjectMother = new RestaurantObjectMother();

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    private static final String RESTAURANT_LAST_UPDATED = LocalDateTime.now().toString();

    private static final String RESTAURANT_NEW_ADDRESS = "321 AI Avenue, Tech City";

    private static final String OWNER_ID = UUID.randomUUID().toString();

    private static final String OWNER_LAST_UPDATED = LocalDateTime.now().toString();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.restaurantSampleData = new LinkedHashMap<>();
        this.restaurantSampleData.put("id", RESTAURANT_ID);
        this.restaurantSampleData.put("name", "The Gemini Grill");
        this.restaurantSampleData.put("address", "123 AI Avenue, Tech City");
        this.restaurantSampleData.put("cuisineType", "Japanese");
        this.restaurantSampleData.put("businessHours", getBusinessHoursSampleData());
        this.restaurantSampleData.put("ownerId", OWNER_ID);
        this.restaurantSampleData.put("owner", getOwnerSampleData());
        this.restaurantSampleData.put("lastUpdated", RESTAURANT_LAST_UPDATED);
    }

    private static Map<String, Object> getOwnerSampleData() {
        Map<String, Object> ownerSampleData = new LinkedHashMap<>();
        ownerSampleData.put("id", OWNER_ID);
        ownerSampleData.put("name", "Marcos");
        ownerSampleData.put("userType", UserType.RESTAURANT_OWNER.getValue());
        ownerSampleData.put("email", "marcos@mail.com");
        ownerSampleData.put("login", "marcos635");
        ownerSampleData.put("password", "09231s121!G");
        ownerSampleData.put("address", "82495 Xavier Keys, Emersonburgh, KS 65336-8213");
        ownerSampleData.put("lastUpdated", OWNER_LAST_UPDATED);
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
    @DisplayName("should update a Restaurant if it exists in the database")
    void shouldUpdateRestaurant() {
        // Given
        Map<DayOfWeek, DailySchedule> newBusinessHours = new LinkedHashMap<>();
        newBusinessHours.put(
                DayOfWeek.FRIDAY,
                DailySchedule
                        .builder()
                        .openingTime(LocalTime.parse("11:00"))
                        .closingTime(LocalTime.parse("23:59"))
                        .build());
        newBusinessHours.put(
                DayOfWeek.SATURDAY,
                DailySchedule
                        .builder()
                        .openingTime(LocalTime.parse("11:00"))
                        .closingTime(LocalTime.parse("23:00"))
                        .build());
        final RestaurantEntity restaurantEntityWithUpdates = RestaurantEntity
                .builder()
                .address(RESTAURANT_NEW_ADDRESS)
                .businessHours(newBusinessHours)
                .owner(this.userObjectMother.createSampleUser(getOwnerSampleData()))
                .lastUpdated(LocalDateTime.now().minusHours(3))
                .build();
        final RestaurantEntity foundRestaurantEntity = this.restaurantObjectMother
                .buildRestaurantEntity(this.restaurantSampleData);
        when(this.mockRestaurantGateway
                .findById(UUID.fromString(RESTAURANT_ID)))
                .thenReturn(Optional.of(foundRestaurantEntity));
        final RestaurantEntity updatedRestaurantEntity = restaurantEntityWithUpdates.toBuilder().build();
        when(this.mockRestaurantGateway.save(any(RestaurantEntity.class))).thenReturn(updatedRestaurantEntity);

        // When
        final RestaurantEntity restaurantEntity = this.useCase
                .execute(UUID.fromString(RESTAURANT_ID), RESTAURANT_NEW_ADDRESS, newBusinessHours);

        // Then
        assertThat(restaurantEntity).isNotNull().isEqualTo(updatedRestaurantEntity);
        verify(this.mockRestaurantGateway, times(1)).findById(UUID.fromString(RESTAURANT_ID));
        final ArgumentCaptor<RestaurantEntity> argument = ArgumentCaptor.forClass(RestaurantEntity.class);
        verify(this.mockRestaurantGateway, times(1)).save(argument.capture());
        final RestaurantEntity capturedRestaurantEntity = argument.getValue();
        final RestaurantEntity expectedRestaurantEntity = foundRestaurantEntity
                .toBuilder()
                .address(RESTAURANT_NEW_ADDRESS)
                .businessHours(newBusinessHours)
                .build();
        assertThat(capturedRestaurantEntity).usingRecursiveComparison().isEqualTo(expectedRestaurantEntity);
    }

    @Test
    @DisplayName("should throw a EntityNotFoundException when the restaurant is not found in the database")
    void shouldThrowEntityNotFoundException() {
        // Given
        final RestaurantEntity restaurantEntityWithUpdates = RestaurantEntity
                .builder()
                .address(RESTAURANT_NEW_ADDRESS)
                .businessHours(null)
                .owner(this.userObjectMother.createSampleUser(getOwnerSampleData()))
                .lastUpdated(LocalDateTime.now().minusHours(3))
                .build();
        when(this.mockRestaurantGateway.findById(UUID.fromString(RESTAURANT_ID))).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(UUID.fromString(RESTAURANT_ID), RESTAURANT_NEW_ADDRESS, null))
                .isInstanceOf(EntityNotFoundException.class);
        verify(this.mockRestaurantGateway, times(1)).findById(UUID.fromString(RESTAURANT_ID));
        verify(this.mockRestaurantGateway, times(0)).save(restaurantEntityWithUpdates);
    }
}
