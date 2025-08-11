package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
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

class CreateUserEntityUseCaseTest {
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
        final UserEntity newUserEntity = UserEntity
                .builder()
                .email("test_user@example.com")
                .login("test_user")
                .password("Str0ngPwd@62")
                .build();
        final UserEntity createdUserEntity = newUserEntity.toBuilder().build();
        when(this.mockUserGateway.findByLogin(newUserEntity.getLogin())).thenReturn(Optional.empty());
        when(this.mockUserGateway.save(newUserEntity)).thenReturn(createdUserEntity);

        // When
        final UserEntity userEntity = this.useCase.execute(newUserEntity);

        // Then
        assertThat(userEntity).isNotNull().isEqualTo(createdUserEntity);
        verify(this.mockUserGateway, times(1)).findByLogin(newUserEntity.getLogin());
        verify(this.mockUserGateway, times(1)).save(newUserEntity);
    }

    @Test
    @DisplayName("should throw a EntityAlreadyExistsException when the new user is found in the database before its creation")
    void shouldThrowUserAlreadyExist() {
        // Given
        final UserEntity newUserEntity = UserEntity
                .builder()
                .email("test_user@example.com")
                .login("test_user")
                .password("Str0ngPwd@62")
                .build();
        when(this.mockUserGateway.findByLogin(newUserEntity.getLogin())).thenReturn(Optional.of(newUserEntity));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(newUserEntity)).isInstanceOf(EntityAlreadyExistsException.class);
        verify(this.mockUserGateway, times(1)).findByLogin(newUserEntity.getLogin());
        verify(this.mockUserGateway, times(0)).save(newUserEntity);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }
}
