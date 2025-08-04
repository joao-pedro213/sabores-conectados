package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteRestaurantByIdUseCaseTest {
    @Mock
    private RestaurantGateway mockRestaurantGateway;

    @InjectMocks
    private DeleteRestaurantByIdUseCase useCase;

    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should delete a Restaurant from the database")
    void shouldDeleteRestaurantById() {
        // When
        this.useCase.execute(ID);

        // Then
        verify(this.mockRestaurantGateway, times(1)).deleteById(ID);
    }
}
