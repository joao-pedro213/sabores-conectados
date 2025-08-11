package com.postech.saboresconectados.infrastructure.item.data.mapper;

import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.dto.NewItemDto;
import com.postech.saboresconectados.core.restaurant.dto.RestaurantDto;
import com.postech.saboresconectados.core.item.dto.UpdateItemDto;
import com.postech.saboresconectados.infrastructure.item.dto.ItemResponseDto;
import com.postech.saboresconectados.infrastructure.item.dto.NewItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.dto.UpdateItemRequestDto;
import com.postech.saboresconectados.infrastructure.item.data.model.ItemModel;
import com.postech.saboresconectados.infrastructure.restaurant.data.model.RestaurantModel;
import com.postech.saboresconectados.infrastructure.restaurant.data.mapper.RestaurantMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

class ItemMapperTest {
    private RestaurantDto restaurantDto;

    private RestaurantModel restaurantModel;

    private static final UUID ITEM_ID = UUID.randomUUID();
    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        this.restaurantDto = RestaurantDto.builder()
                .id(RESTAURANT_ID)
                .name("Test Restaurant")
                .build();
        this.restaurantModel = RestaurantModel.builder()
                .id(RESTAURANT_ID)
                .name("Test Restaurant")
                .build();
    }

    @Test
    void shouldMapItemDtoToItemModel() {
        // Given
        ItemDto itemDto = ItemDto
                .builder()
                .id(ITEM_ID)
                .restaurant(restaurantDto)
                .name("Test Item")
                .description("Test Description")
                .price(new BigDecimal("15.99"))
                .availableOnlyAtRestaurant(true)
                .photoPath("/path/to/photo.jpg")
                .lastUpdated(LocalDateTime.now())
                .build();
        try (MockedStatic<RestaurantMapper> mockedStaticRestaurantMapper = mockStatic(RestaurantMapper.class)) {
            mockedStaticRestaurantMapper
                    .when(() -> RestaurantMapper.toRestaurantModel(this.restaurantDto))
                    .thenReturn(this.restaurantModel);

            // When
            ItemModel itemModel = ItemMapper.toItemModel(itemDto);

            // Then
            assertThat(itemModel).isNotNull();
            assertThat(itemModel.getId()).isEqualTo(itemDto.getId());
            assertThat(itemModel.getRestaurant().getId()).isEqualTo(itemDto.getRestaurant().getId());
            assertThat(itemModel.getName()).isEqualTo(itemDto.getName());
            assertThat(itemModel.getDescription()).isEqualTo(itemDto.getDescription());
            assertThat(itemModel.getPrice()).isEqualTo(itemDto.getPrice());
            assertThat(itemModel.getAvailableOnlyAtRestaurant()).isEqualTo(itemDto.getAvailableOnlyAtRestaurant());
            assertThat(itemModel.getPhotoPath()).isEqualTo(itemDto.getPhotoPath());
            assertThat(itemModel.getLastUpdated()).isEqualTo(itemDto.getLastUpdated());
        }
    }

    @Test
    void shouldMapItemModelToItemDto() {
        // Given
        ItemModel itemModel = ItemModel.builder()
                .id(ITEM_ID)
                .restaurant(restaurantModel)
                .name("Test Item")
                .description("Test Description")
                .price(new BigDecimal("15.99"))
                .availableOnlyAtRestaurant(true)
                .photoPath("/path/to/photo.jpg")
                .lastUpdated(LocalDateTime.now())
                .build();
        try (MockedStatic<RestaurantMapper> mockedStaticRestaurantMapper = mockStatic(RestaurantMapper.class)) {
            mockedStaticRestaurantMapper
                    .when(() -> RestaurantMapper.toRestaurantDto(this.restaurantModel))
                    .thenReturn(this.restaurantDto);
            // When
            ItemDto itemDto = ItemMapper.toItemDto(itemModel);

            // Then
            assertThat(itemDto).isNotNull();
            assertThat(itemDto.getId()).isEqualTo(itemModel.getId());
            assertThat(itemDto.getRestaurant().getId()).isEqualTo(itemModel.getRestaurant().getId());
            assertThat(itemDto.getName()).isEqualTo(itemModel.getName());
            assertThat(itemDto.getDescription()).isEqualTo(itemModel.getDescription());
            assertThat(itemDto.getPrice()).isEqualTo(itemModel.getPrice());
            assertThat(itemDto.getAvailableOnlyAtRestaurant()).isEqualTo(itemModel.getAvailableOnlyAtRestaurant());
            assertThat(itemDto.getPhotoPath()).isEqualTo(itemModel.getPhotoPath());
            assertThat(itemDto.getLastUpdated()).isEqualTo(itemModel.getLastUpdated());
        }
    }

    @Test
    void shouldMapNewItemRequestDtoToNewItemDto() {
        // Given
        NewItemRequestDto newItemRequestDto = NewItemRequestDto.builder()
                .restaurantId(RESTAURANT_ID)
                .name("New Item")
                .description("New Description")
                .price(new BigDecimal("20.50"))
                .availableOnlyAtRestaurant(false)
                .photoPath("/path/to/new-item.jpg")
                .build();

        // When
        NewItemDto newItemDto = ItemMapper.toNewItemDto(newItemRequestDto);

        // Then
        assertThat(newItemDto).isNotNull();
        assertThat(newItemDto.getRestaurantId()).isEqualTo(newItemRequestDto.getRestaurantId());
        assertThat(newItemDto.getName()).isEqualTo(newItemRequestDto.getName());
        assertThat(newItemDto.getDescription()).isEqualTo(newItemRequestDto.getDescription());
        assertThat(newItemDto.getPrice()).isEqualTo(newItemRequestDto.getPrice());
        assertThat(newItemDto.getAvailableOnlyAtRestaurant()).isEqualTo(newItemRequestDto.getAvailableOnlyAtRestaurant());
        assertThat(newItemDto.getPhotoPath()).isEqualTo(newItemRequestDto.getPhotoPath());
    }

    @Test
    void shouldMapUpdateItemRequestDtoToUpdateItemDto() {
        // Given
        UpdateItemRequestDto updateItemRequestDto = UpdateItemRequestDto.builder()
                .name("Updated Item")
                .description("Updated Description")
                .price(new BigDecimal("18.00"))
                .availableOnlyAtRestaurant(false)
                .photoPath("/path/to/updated-photo.jpg")
                .build();

        // When
        UpdateItemDto updateItemDto = ItemMapper.toUpdateItemDto(updateItemRequestDto);

        // Then
        assertThat(updateItemDto).isNotNull();
        assertThat(updateItemDto.getName()).isEqualTo(updateItemRequestDto.getName());
        assertThat(updateItemDto.getDescription()).isEqualTo(updateItemRequestDto.getDescription());
        assertThat(updateItemDto.getPrice()).isEqualTo(updateItemRequestDto.getPrice());
        assertThat(updateItemDto.getAvailableOnlyAtRestaurant()).isEqualTo(updateItemRequestDto.getAvailableOnlyAtRestaurant());
        assertThat(updateItemDto.getPhotoPath()).isEqualTo(updateItemRequestDto.getPhotoPath());
    }

    @Test
    void shouldMapItemDtoToItemResponseDto() {
        // Given
        ItemDto itemDto = ItemDto.builder()
                .id(ITEM_ID)
                .restaurant(restaurantDto)
                .name("Response Item")
                .description("Response Description")
                .price(new BigDecimal("10.99"))
                .availableOnlyAtRestaurant(true)
                .photoPath("/path/to/response-item.jpg")
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        ItemResponseDto itemResponseDto = ItemMapper.toItemResponseDto(itemDto);

        // Then
        assertThat(itemResponseDto).isNotNull();
        assertThat(itemResponseDto.getId()).isEqualTo(itemDto.getId());
        // This is the key assertion from our previous discussion
        assertThat(itemResponseDto.getRestaurantId()).isEqualTo(itemDto.getRestaurant().getId());
        assertThat(itemResponseDto.getName()).isEqualTo(itemDto.getName());
        assertThat(itemResponseDto.getDescription()).isEqualTo(itemDto.getDescription());
        assertThat(itemResponseDto.getPrice()).isEqualTo(itemDto.getPrice());
        assertThat(itemResponseDto.getAvailableOnlyAtRestaurant()).isEqualTo(itemDto.getAvailableOnlyAtRestaurant());
        assertThat(itemResponseDto.getPhotoPath()).isEqualTo(itemDto.getPhotoPath());
        assertThat(itemResponseDto.getLastUpdated()).isEqualTo(itemDto.getLastUpdated());
    }
}
