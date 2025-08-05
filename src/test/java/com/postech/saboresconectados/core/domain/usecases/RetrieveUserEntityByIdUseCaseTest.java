package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RetrieveUserEntityByIdUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private RetrieveUserByIdUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private static final UUID ID = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticEmailValidator = mockStatic(EmailValidator.class);
        this.mockedStaticEmailValidator.when(EmailValidator::getInstance).thenReturn(this.mockEmailValidator);
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(true);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }

    @Test
    @DisplayName("Should find a User if it exists in the database")
    void shouldFindUserById() {
        // Given
        final UserEntity foundUserEntity = UserEntity
                .builder()
                .id(ID)
                .email("test_user@example.com")
                .login("test_user")
                .password("Kdfv#a41")
                .build();
        when(this.mockUserGateway.findById(ID)).thenReturn(Optional.of(foundUserEntity));

        // When
        final UserEntity userEntity = this.useCase.execute(ID);

        // Then
        assertThat(userEntity).isNotNull().isEqualTo(foundUserEntity);
        verify(this.mockUserGateway, times(1)).findById(ID);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the user is not found in the database")
    void shouldThrowEntityNotFoundException() {
        // Given
        when(this.mockUserGateway.findById(ID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(ID)).isInstanceOf(EntityNotFoundException.class);
        verify(this.mockUserGateway, times(1)).findById(ID);
    }
}
