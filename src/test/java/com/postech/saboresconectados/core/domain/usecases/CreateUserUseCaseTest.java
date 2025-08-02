package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.UserAlreadyExistsException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreateUserUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private CreateUserUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticEmailValidator = mockStatic(EmailValidator.class);
        this.mockedStaticEmailValidator.when(EmailValidator::getInstance).thenReturn(this.mockEmailValidator);
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(true);
    }

    @Test
    @DisplayName("Should create a new User if it doesn't exist in the database yet")
    void shouldCreateUser() {
        // Given
        final User newUser = User
                .builder()
                .email("test_user@example.com")
                .login("test_user")
                .password("Str0ngPwd@62")
                .build();
        final User createdUser = newUser.toBuilder().build();
        when(this.mockUserGateway.findByLogin(newUser.getLogin())).thenReturn(Optional.empty());
        when(this.mockUserGateway.save(newUser)).thenReturn(createdUser);

        // When
        final User user = this.useCase.execute(newUser);

        // Then
        assertThat(user).isNotNull().isEqualTo(createdUser);
        verify(this.mockUserGateway, times(1)).findByLogin(newUser.getLogin());
        verify(this.mockUserGateway, times(1)).save(newUser);
    }

    @Test
    @DisplayName("should throw a UserAlreadyExistsException when the new user is found in the database before its creation")
    void shouldThrowUserAlreadyExist() {
        // Given
        final User newUser = User
                .builder()
                .email("test_user@example.com")
                .login("test_user")
                .password("Str0ngPwd@62")
                .build();
        when(this.mockUserGateway.findByLogin(newUser.getLogin())).thenReturn(Optional.of(newUser));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(newUser)).isInstanceOf(UserAlreadyExistsException.class);
        verify(this.mockUserGateway, times(1)).findByLogin(newUser.getLogin());
        verify(this.mockUserGateway, times(0)).save(newUser);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }
}
