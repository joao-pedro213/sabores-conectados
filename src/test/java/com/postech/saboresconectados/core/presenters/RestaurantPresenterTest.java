package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.RestaurantEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.helpers.RestaurantObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RestaurantPresenterTest {

    private RestaurantPresenter presenter;

    private Map<String, Object> restaurantSampleData;

    private final RestaurantObjectMother restaurantObjectMother = new RestaurantObjectMother();

    private static final String OWNER_ID = UUID.randomUUID().toString();

    private static final String RESTAURANT_ID = UUID.randomUUID().toString();

    private static final String RESTAURANT_LAST_UPDATED = LocalDateTime.now().toString();

    @BeforeEach
    void setUp() {
        this.presenter = RestaurantPresenter.build();
        this.restaurantSampleData = new LinkedHashMap<>();
        this.restaurantSampleData.put("id", RESTAURANT_ID);
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
        ownerSampleData.put("id", OWNER_ID);
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

    @Test
    void shouldMapDomainToDto() {
        // Given
        final RestaurantEntity restaurantEntity = this.restaurantObjectMother.buildRestaurantEntity(this.restaurantSampleData);

        // When
        final RestaurantDto restaurantDto = this.presenter.toDto(restaurantEntity);

        // Then
        final RestaurantDto expectedRestaurantDto = this.restaurantObjectMother
                .buildRestaurantDto(this.restaurantSampleData);
        assertThat(restaurantDto)
                .usingRecursiveComparison()
                .ignoringFields("owner.password")
                .isEqualTo(expectedRestaurantDto);
    }
}
