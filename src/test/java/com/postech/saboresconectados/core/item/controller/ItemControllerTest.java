package com.postech.saboresconectados.core.item.controller;

import com.postech.saboresconectados.core.item.domain.entity.ItemEntity;
import com.postech.saboresconectados.core.item.domain.usecase.CreateItemUseCase;
import com.postech.saboresconectados.core.item.domain.usecase.DeleteItemByIdUseCase;
import com.postech.saboresconectados.core.item.domain.usecase.UpdateItemUseCase;
import com.postech.saboresconectados.core.item.dto.ItemDto;
import com.postech.saboresconectados.core.item.dto.NewItemDto;
import com.postech.saboresconectados.core.item.dto.UpdateItemDto;
import com.postech.saboresconectados.core.item.gateway.ItemGateway;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.item.datasource.ItemDataSource;
import com.postech.saboresconectados.core.restaurant.datasource.RestaurantDataSource;
import com.postech.saboresconectados.core.item.presenter.ItemPresenter;
import com.postech.saboresconectados.helpers.ItemObjectMother;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemControllerTest {
    @Mock
    ItemDataSource itemDataSource;

    @Mock
    RestaurantDataSource restaurantDataSource;

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemPresenter mockItemPresenter;

    private MockedStatic<ItemPresenter> mockedStaticItemPresenter;

    private Map<String, Object> itemSampleData;

    private static final UUID ITEM_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticItemPresenter = mockStatic(ItemPresenter.class);
        this.mockedStaticItemPresenter.when(ItemPresenter::build).thenReturn(this.mockItemPresenter);
        this.itemSampleData = new HashMap<>();
        this.itemSampleData.put("restaurantId", UUID.randomUUID());
        this.itemSampleData.put("name", "Pepperoni Pizza");
        this.itemSampleData.put("description", "A delicious Pepperoni Pizza");
        this.itemSampleData.put("price", BigDecimal.valueOf(25.0));
        this.itemSampleData.put("availableOnlyAtRestaurant", false);
        this.itemSampleData.put("photoPath", "/peperoni-pizza.jpg");
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticItemPresenter != null) {
            this.mockedStaticItemPresenter.close();
        }
    }

    @Test
    void shouldCreateItem() {
        // Given
        final NewItemDto newItemDto = ItemObjectMother.buildNewItemDto(this.itemSampleData);
        final ItemEntity createdItem = ItemEntity.builder().build();
        final ItemDto mappedItemDto = ItemDto.builder().build();
        CreateItemUseCase mockUseCase = mock(CreateItemUseCase.class);
        when(
                mockUseCase
                        .execute(
                                newItemDto.getRestaurantId(),
                                newItemDto.getName(),
                                newItemDto.getDescription(),
                                newItemDto.getPrice(),
                                newItemDto.getAvailableOnlyAtRestaurant(),
                                newItemDto.getPhotoPath())).thenReturn(createdItem);
        when(this.mockItemPresenter.toDto(any(ItemEntity.class))).thenReturn(mappedItemDto);
        try (MockedStatic<CreateItemUseCase> mockedStaticUseCase = mockStatic(CreateItemUseCase.class)) {
            mockedStaticUseCase.when(() -> CreateItemUseCase.build(any(ItemGateway.class), any(RestaurantGateway.class))).thenReturn(mockUseCase);

            // When
            ItemDto itemDto = this.controller.createItem(newItemDto);

            // Then
            assertThat(itemDto).isNotNull().isEqualTo(mappedItemDto);
        }
    }

    @Test
    void shouldUpdateItem() {
        // Given
        final UpdateItemDto updateItemDto = ItemObjectMother.buildUpdateItemDto(this.itemSampleData);
        final ItemEntity updatedItem = ItemEntity.builder().build();
        final ItemDto mappedItemDto = ItemDto.builder().build();
        UpdateItemUseCase mockUseCase = mock(UpdateItemUseCase.class);
        when(
                mockUseCase
                        .execute(
                                ITEM_ID,
                                updateItemDto.getName(),
                                updateItemDto.getDescription(),
                                updateItemDto.getPrice(),
                                updateItemDto.getAvailableOnlyAtRestaurant(),
                                updateItemDto.getPhotoPath())).thenReturn(updatedItem);
        when(this.mockItemPresenter.toDto(any(ItemEntity.class))).thenReturn(mappedItemDto);
        try (MockedStatic<UpdateItemUseCase> mockedStaticUseCase = mockStatic(UpdateItemUseCase.class)) {
            mockedStaticUseCase.when(() -> UpdateItemUseCase.build(any(ItemGateway.class))).thenReturn(mockUseCase);

            // When
            ItemDto itemDto = this.controller.updateItem(ITEM_ID, updateItemDto);

            // Then
            assertThat(itemDto).isNotNull().isEqualTo(mappedItemDto);
        }
    }

    @Test
    void shouldDeleteItemById() {
        // Given
        DeleteItemByIdUseCase mockUseCase = mock(DeleteItemByIdUseCase.class);
        try (MockedStatic<DeleteItemByIdUseCase> mockedStaticUseCase = mockStatic(DeleteItemByIdUseCase.class)) {
            mockedStaticUseCase.when(() -> DeleteItemByIdUseCase.build(any(ItemGateway.class))).thenReturn(mockUseCase);

            // When
            this.controller.deleteItemById(ITEM_ID);

            // Then
            verify(mockUseCase, times(1)).execute(ITEM_ID);
        }
    }
}
