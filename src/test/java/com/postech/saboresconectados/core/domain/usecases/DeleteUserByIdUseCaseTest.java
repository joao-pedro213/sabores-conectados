package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.gateways.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteUserByIdUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private DeleteUserByIdUseCase useCase;

    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should delete a User from the database")
    void shouldFindUserById() {
        // When
        this.useCase.execute(ID);

        // Then
        verify(this.mockUserGateway, times(1)).deleteById(ID);
    }
}
