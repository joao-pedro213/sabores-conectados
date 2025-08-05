package com.postech.saboresconectados.infrastructure.data;

import com.postech.saboresconectados.core.dtos.ItemDto;
import com.postech.saboresconectados.infrastructure.data.datamappers.ItemMapper;
import com.postech.saboresconectados.infrastructure.data.models.ItemModel;
import com.postech.saboresconectados.infrastructure.data.repositories.ItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ItemDataSourceJpaTest {
    @Mock
    private ItemRepository repository;

    @InjectMocks
    private ItemDataSourceJpa dataSource;

    private MockedStatic<ItemMapper> mockedStaticItemMapper;

    private static final UUID ITEM_ID = UUID.randomUUID();

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticItemMapper = mockStatic(ItemMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticItemMapper != null) {
            this.mockedStaticItemMapper.close();
        }
    }

    @Test
    void shouldSaveRestaurant() {
        // Given
        final ItemDto itemDtoToSave = ItemDto.builder().build();
        final ItemModel itemToSave = ItemModel.builder().build();
        final ItemDto expectedSavedItemDto = ItemDto.builder().build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemModel(itemDtoToSave))
                .thenReturn(itemToSave);
        when(this.repository.save(itemToSave)).thenReturn(itemToSave);
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemDto(itemToSave))
                .thenReturn(expectedSavedItemDto);

        // When
        final ItemDto savedItemDto = this.dataSource.save(itemDtoToSave);

        // Then
        this.mockedStaticItemMapper
                .verify(() -> ItemMapper.toItemModel(itemDtoToSave), times(1));
        verify(this.repository, times(1)).save(itemToSave);
        this.mockedStaticItemMapper
                .verify(() -> ItemMapper.toItemDto(itemToSave), times(1));
        assertThat(savedItemDto).isNotNull().isEqualTo(expectedSavedItemDto);
    }

    @Test
    void shouldFindAllByRestaurantById() {
        // Given
        final List<ItemModel> foundItems = List.of(ItemModel.builder().build());
        when(this.repository.findAllByRestaurantId(RESTAURANT_ID)).thenReturn(foundItems);
        final ItemDto mappedItemDto = ItemDto.builder().build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemDto(foundItems.getFirst()))
                .thenReturn(mappedItemDto);

        // When
        List<ItemDto> foundItemDtos = this.dataSource.findAllByRestaurantId(RESTAURANT_ID);

        // Then
        verify(this.repository, times(1)).findAllByRestaurantId(RESTAURANT_ID);
        this.mockedStaticItemMapper.verify(() -> ItemMapper.toItemDto(foundItems.getFirst()), times(1));
        assertThat(foundItemDtos).hasSize(1).contains(mappedItemDto);
    }

    @Test
    void shouldFindItemById() {
        // Given
        final ItemModel foundItem = ItemModel.builder().build();
        when(this.repository.findById(ITEM_ID)).thenReturn(Optional.of(foundItem));
        final ItemDto mappedItemDto = ItemDto.builder().build();
        this.mockedStaticItemMapper
                .when(() -> ItemMapper.toItemDto(foundItem))
                .thenReturn(mappedItemDto);

        // When
        Optional<ItemDto> foundItemDto = this.dataSource.findById(ITEM_ID);

        // Then
        verify(this.repository, times(1)).findById(ITEM_ID);
        this.mockedStaticItemMapper.verify(() -> ItemMapper.toItemDto(foundItem), times(1));
        assertThat(foundItemDto).isPresent().contains(mappedItemDto);
    }

    @Test
    void shouldDeleteItemById() {
        // When
        this.dataSource.deleteById(ITEM_ID);

        // Then
        verify(this.repository, times(1)).deleteById(ITEM_ID);
    }
}
