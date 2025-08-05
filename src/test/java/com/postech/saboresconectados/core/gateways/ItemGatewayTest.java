package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.helpers.ItemObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemGatewayTest {
    @Mock
    private ItemDataSource dataSource;

    @InjectMocks
    private ItemGateway gateway;

    private Map<String, Object> itemSampleData;

    private static final UUID ITEM_ID = UUID.randomUUID();

    private static final LocalDateTime ITEM_LAST_UPDATED = LocalDateTime.now();

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    private static final LocalDateTime RESTAURANT_LAST_UPDATED = LocalDateTime.now();

    private static final UUID OWNER_ID = UUID.randomUUID();

    private static final LocalDateTime OWNER_LAST_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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
    void shouldSaveItem() {
        // Given
        final ItemEntity itemToSave = ItemObjectMother.buildItemEntity(this.itemSampleData);
        final ItemDto savedItemDto = ItemObjectMother.buildItemDto(this.itemSampleData);
        when(this.dataSource.save(any(ItemDto.class))).thenReturn(savedItemDto);

        // When
        final ItemEntity savedItem = this.gateway.save(itemToSave);

        // Then
        final ArgumentCaptor<ItemDto> argument = ArgumentCaptor.forClass(ItemDto.class);
        verify(this.dataSource, times(1)).save(argument.capture());
        final ItemDto capturedItemDto = argument.getValue();
        final ItemDto expectedItemDto = savedItemDto.toBuilder().build();
        assertThat(capturedItemDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedItemDto);
        assertThat(savedItem).isNotNull();
        final ItemEntity expectedUpdatedItem = itemToSave.toBuilder().build();
        assertThat(savedItem).usingRecursiveComparison().isEqualTo(expectedUpdatedItem);
    }

    @Test
    void shouldFindItemById() {
        // Given
        final ItemDto foundItemDto = ItemObjectMother.buildItemDto(this.itemSampleData);
        when(this.dataSource.findById(ITEM_ID)).thenReturn(Optional.of(foundItemDto));

        // When
        Optional<ItemEntity> foundItem = this.gateway.findById(ITEM_ID);

        // Then
        verify(this.dataSource, times(1)).findById(ITEM_ID);
        assertThat(foundItem).isPresent();
        final ItemEntity expectedFoundItem = ItemObjectMother.buildItemEntity(this.itemSampleData);
        assertThat(foundItem.get()).usingRecursiveComparison().isEqualTo(expectedFoundItem);
    }

    @Test
    void shouldFindAllByRestaurantId() {
        // Given
        final List<ItemDto> foundItemDtos = List.of(ItemObjectMother.buildItemDto(this.itemSampleData));
        when(this.dataSource.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(foundItemDtos);

        // When
        List<ItemEntity> foundItems = this.gateway.findAllByRestaurantId(RESTAURANT_ID);

        // Then
        verify(this.dataSource, times(1)).findAllByRestaurantId(RESTAURANT_ID);
        assertThat(foundItems).hasSize(1);
        final ItemEntity expectedFoundItem = ItemObjectMother.buildItemEntity(this.itemSampleData);
        assertThat(foundItems.stream().findFirst().get()).usingRecursiveComparison().isEqualTo(expectedFoundItem);
    }

    @Test
    void shouldDeleteItemById() {
        // When
        this.gateway.deleteById(ITEM_ID);

        // Then
        verify(this.dataSource, times(1)).deleteById(ITEM_ID);
    }
}
