package com.postech.saboresconectados.core.item.presenter;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.helpers.ItemObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ItemPresenterTest {
    private ItemPresenter presenter;

    private Map<String, Object> itemSampleData;

    private static final UUID ITEM_ID = UUID.randomUUID();

    private static final LocalDateTime ITEM_LAST_UPDATED = LocalDateTime.now();

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    private static final LocalDateTime RESTAURANT_LAST_UPDATED = LocalDateTime.now();

    private static final UUID OWNER_ID = UUID.randomUUID();

    private static final LocalDateTime OWNER_LAST_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        this.presenter = ItemPresenter.build();
        this.itemSampleData = new HashMap<>();
        this.itemSampleData.put("id", ITEM_ID.toString());
        this.itemSampleData.put("restaurant", getRestaurantSampleData());
        this.itemSampleData.put("name", "Pepperoni Pizza");
        this.itemSampleData.put("description", "A delicious Pepperoni Pizza");
        this.itemSampleData.put("price", BigDecimal.valueOf(25.0));
        this.itemSampleData.put("availableOnlyAtRestaurant", false);
        this.itemSampleData.put("photoPath", "/peperoni-pizza.jpg");
        this.itemSampleData.put("lastUpdated", ITEM_LAST_UPDATED.toString());
    }

    private static Map<String, Object> getRestaurantSampleData() {
        Map<String, Object> restaurantSampleData = new LinkedHashMap<>();
        restaurantSampleData.put("id", RESTAURANT_ID.toString());
        restaurantSampleData.put("name", "The Gemini Grill");
        restaurantSampleData.put("address", "123 AI Avenue, Tech City");
        restaurantSampleData.put("cuisineType", "Japanese");
        restaurantSampleData.put("owner", getOwnerSampleData());
        restaurantSampleData.put("businessHours", getBusinessHoursSampleData());
        restaurantSampleData.put("lastUpdated", RESTAURANT_LAST_UPDATED.toString());
        return restaurantSampleData;
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

    @Test
    void shouldMapDomainToDto() {
        // Given
        final ItemEntity item = ItemObjectMother.buildItemEntity(this.itemSampleData);

        // When
        final ItemDto itemDto = this.presenter.toDto(item);

        // Then
        final ItemDto expectedItemDto = ItemObjectMother.buildItemDto(this.itemSampleData);

        assertThat(itemDto)
                .usingRecursiveComparison()
                .ignoringFields("restaurant.owner.password")
                .isEqualTo(expectedItemDto);
    }
}