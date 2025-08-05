package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ChangeUserEntityPasswordUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private ChangeUserPasswordUseCase useCase;

    private static final String LOGIN = "test3";

    private static final String OLD_PASSWORD = "p4ssT3s!";

    private static final String NEW_PASSWORD = "N3w!p4ss";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("should change a User's password if the user exists in the database and the provided current password matches the one on file")
    void shouldChangeUserPassword() {
        // Given
        final UserEntity foundUserEntity = UserEntity
                .builder()
                .email("user@example.com")
                .login(LOGIN)
                .password(OLD_PASSWORD)
                .build();
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.of(foundUserEntity));

        // When
        this.useCase.execute(LOGIN, OLD_PASSWORD, NEW_PASSWORD);

        // Then
        verify(this.mockUserGateway, times(1)).findByLogin(LOGIN);
        final ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.mockUserGateway, times(1)).save(argument.capture());
        final UserEntity capturedUserEntity = argument.getValue();
        final UserEntity expectedUserEntity = foundUserEntity.toBuilder().password(NEW_PASSWORD).build();
        assertThat(capturedUserEntity).usingRecursiveComparison().isEqualTo(expectedUserEntity);
    }

    @Test
    @DisplayName("should throw InvalidLoginCredentialsException when the user is not found in the database")
    void shouldThrowInvalidLoginCredentialsExceptionWhenUserIsNotFound() {
        // Given
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(LOGIN, OLD_PASSWORD, NEW_PASSWORD))
                .isInstanceOf(InvalidLoginCredentialsException.class);
    }

    @Test
    @DisplayName("should throw InvalidLoginCredentialsException when the user is found in the database, but the provided 'old' password is invalid")
    void shouldThrowInvalidLoginCredentialsExceptionWhenUserCredentialsAreInvalid() {
        // Given
        final UserEntity foundUserEntity = UserEntity
                .builder()
                .email("user@example.com")
                .login(LOGIN)
                .password(OLD_PASSWORD)
                .build();
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.of(foundUserEntity));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(LOGIN, "wr0ng0ldp4ss", NEW_PASSWORD))
                .isInstanceOf(InvalidLoginCredentialsException.class);
    }
}
