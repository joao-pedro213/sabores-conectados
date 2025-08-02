package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
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

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginUserUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private LoginUserUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private static final String LOGIN = "test1";

    private static final String PASSWORD = "t3s!P4ss";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticEmailValidator = mockStatic(EmailValidator.class);
        this.mockedStaticEmailValidator.when(EmailValidator::getInstance).thenReturn(this.mockEmailValidator);
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(true);
    }

    @Test
    @DisplayName("should login a User if the it exists in the database and if the provided credentials are valid")
    void shouldLoginUser() {
        // Given
        final User foundUser = User
                .builder()
                .email("test@domain.com")
                .login(LOGIN)
                .password(PASSWORD)
                .build();
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.of(foundUser));

        // When
        this.useCase.execute(LOGIN, PASSWORD);

        // Then
        verify(this.mockUserGateway, times(1)).findByLogin(LOGIN);
    }

    @Test
    @DisplayName("should throw InvalidLoginCredentialsException when the user is not found in the database")
    void shouldThrowInvalidLoginCredentialsExceptionWhenUserIsNotFound() {
        // Given
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(LOGIN, PASSWORD))
                .isInstanceOf(InvalidLoginCredentialsException.class);
    }

    @Test
    @DisplayName("should throw InvalidLoginCredentialsException when the user is found in the database, but the provided credentials are invalid")
    void shouldThrowInvalidLoginCredentialsExceptionWhenUserCredentialsAreInvalid() {
        // Given
        final User foundUser = User
                .builder()
                .email("test@domain.com")
                .login(LOGIN)
                .password("0th3rP4ss!")
                .build();
        when(this.mockUserGateway.findByLogin(LOGIN)).thenReturn(Optional.of(foundUser));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(LOGIN, PASSWORD))
                .isInstanceOf(InvalidLoginCredentialsException.class);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }
}
