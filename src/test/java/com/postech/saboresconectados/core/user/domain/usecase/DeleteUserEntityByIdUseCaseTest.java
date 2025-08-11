package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.gateway.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class DeleteUserEntityByIdUseCaseTest {
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
    void shouldDeleteUserById() {
        // When
        this.useCase.execute(ID);

        // Then
        verify(this.mockUserGateway, times(1)).deleteById(ID);
    }
}
