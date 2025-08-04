package com.postech.saboresconectados.core.controllers;

import com.postech.saboresconectados.core.controller.ItemController;
import com.postech.saboresconectados.core.domain.entities.ItemEntity;
import com.postech.saboresconectados.core.domain.usecases.CreateItemUseCase;
import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.core.dtos.NewItemDto;
import com.postech.saboresconectados.core.gateways.ItemGateway;
import com.postech.saboresconectados.core.interfaces.ItemDataSource;
import com.postech.saboresconectados.core.presenters.ItemPresenter;
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
import static org.mockito.Mockito.when;

class ItemControllerTest {
    @Mock
    ItemDataSource itemDataSource;

    @InjectMocks
    private ItemController controller;

    @Mock
    private ItemPresenter mockItemPresenter;

    private MockedStatic<ItemPresenter> mockedStaticItemPresenter;

    private Map<String, Object> itemSampleData;

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
        final NewItemDto newItemDto = ItemObjectMother.buildINewItemDto(this.itemSampleData);
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
                                newItemDto.isAvailableOnlyAtRestaurant(),
                                newItemDto.getPhotoPath())).thenReturn(createdItem);
        when(this.mockItemPresenter.toDto(any(ItemEntity.class))).thenReturn(mappedItemDto);
        try (MockedStatic<CreateItemUseCase> mockedStaticUseCase = mockStatic(CreateItemUseCase.class)) {
            mockedStaticUseCase.when(() -> CreateItemUseCase.build(any(ItemGateway.class))).thenReturn(mockUseCase);

            // When
            ItemDto itemDto = this.controller.createItem(newItemDto);

            // Then
            assertThat(itemDto).isNotNull().isEqualTo(mappedItemDto);
        }
    }
}
