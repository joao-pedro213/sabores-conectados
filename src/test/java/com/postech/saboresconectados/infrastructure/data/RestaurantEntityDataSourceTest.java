package com.postech.saboresconectados.infrastructure.data;

import com.postech.saboresconectados.core.dtos.RestaurantDto;
import com.postech.saboresconectados.infrastructure.data.datamappers.RestaurantMapper;
import com.postech.saboresconectados.infrastructure.data.models.RestaurantModel;
import com.postech.saboresconectados.infrastructure.data.repositories.RestaurantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RestaurantEntityDataSourceTest {
    @Mock
    private RestaurantRepository repository;

    @InjectMocks
    private RestaurantDataSourceJpa dataSource;

    private MockedStatic<RestaurantMapper> mockedStaticRestaurantMapper;

    private static final UUID ID = UUID.randomUUID();

    private static final String NAME = "Los Pollos Hermanos";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticRestaurantMapper = mockStatic(RestaurantMapper.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticRestaurantMapper != null) {
            this.mockedStaticRestaurantMapper.close();
        }
    }

    @Test
    void shouldSaveRestaurant() {
        // Given
        final RestaurantDto restaurantToSaveDto = RestaurantDto.builder().build();
        final RestaurantModel restaurantToSave = RestaurantModel.builder().build();
        final RestaurantDto expectedSavedRestaurantDto = RestaurantDto.builder().build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantModel(restaurantToSaveDto))
                .thenReturn(restaurantToSave);
        when(this.repository.save(restaurantToSave)).thenReturn(restaurantToSave);
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantDto(restaurantToSave))
                .thenReturn(expectedSavedRestaurantDto);

        // When
        final RestaurantDto savedRestaurantDto = this.dataSource.save(restaurantToSaveDto);

        // Then
        this.mockedStaticRestaurantMapper
                .verify(() -> RestaurantMapper.toRestaurantModel(restaurantToSaveDto), times(1));
        verify(this.repository, times(1)).save(restaurantToSave);
        this.mockedStaticRestaurantMapper
                .verify(() -> RestaurantMapper.toRestaurantDto(restaurantToSave), times(1));
        assertThat(savedRestaurantDto).isNotNull().isEqualTo(expectedSavedRestaurantDto);
    }

    @Test
    void shouldFindRestaurantById() {
        // Given
        final RestaurantModel foundRestaurant = RestaurantModel.builder().build();
        when(this.repository.findById(ID)).thenReturn(Optional.of(foundRestaurant));
        final RestaurantDto mappedRestaurantDto = RestaurantDto.builder().build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantDto(foundRestaurant))
                .thenReturn(mappedRestaurantDto);

        // When
        Optional<RestaurantDto> foundRestaurantDto = this.dataSource.findById(ID);

        // Then
        verify(this.repository, times(1)).findById(ID);
        this.mockedStaticRestaurantMapper.verify(() -> RestaurantMapper.toRestaurantDto(foundRestaurant), times(1));
        assertThat(foundRestaurantDto).isPresent().contains(mappedRestaurantDto);
    }

    @Test
    void shouldFindRestaurantByName() {
        // Given
        final RestaurantModel foundRestaurant = RestaurantModel.builder().build();
        when(this.repository.findByName(NAME)).thenReturn(Optional.of(foundRestaurant));
        final RestaurantDto mappedRestaurantDto = RestaurantDto.builder().build();
        this.mockedStaticRestaurantMapper
                .when(() -> RestaurantMapper.toRestaurantDto(foundRestaurant))
                .thenReturn(mappedRestaurantDto);

        // When
        Optional<RestaurantDto> foundRestaurantDto = this.dataSource.findByName(NAME);

        // Then
        verify(this.repository, times(1)).findByName(NAME);
        this.mockedStaticRestaurantMapper
                .verify(() -> RestaurantMapper.toRestaurantDto(foundRestaurant), times(1));
        assertThat(foundRestaurantDto).isPresent().contains(mappedRestaurantDto);
    }

    @Test
    void shouldDeleteRestaurantById() {
        // When
        this.dataSource.deleteById(ID);

        // Then
        verify(this.repository, times(1)).deleteById(ID);
    }
}
