package com.postech.saboresconectados.core.user.domain.entity;

import com.postech.saboresconectados.core.user.domain.exception.InvalidEmailException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidPasswordException;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class UserEntityTest {
    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private static final String VALID_EMAIL = "test.user@mail.com";

    private static final String VALID_LOGIN = "test.user";

    private static final String VALID_PASSWORD = "P@sswOrd123!";

    private static final String EXPECTED_EMAIl_ERROR_MESSAGE = "Email should have a valid format";

    private static final String EXPECTED_PASSWORD_ERROR_MESSAGE =
            "Password must contain at least one uppercase letter, "
                    + "one lowercase letter, one number, and one special character";

    private static final String EXPECTED_LOGIN_ERROR_MESSAGE =
            "Login can only contain letters, numbers, '.', '_' and '-'";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticEmailValidator = mockStatic(EmailValidator.class);
        this.mockedStaticEmailValidator.when(EmailValidator::getInstance).thenReturn(this.mockEmailValidator);
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(true);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "nopassword", "NOPASSWORD", "PasswordOnly",
            "Password123", "password@1", "PASSWORD@1",
            "WeakPass", "!@#$", "12345",
            ""
    })
    void shouldRaiseExceptionWhenPasswordIsInvalid(String invalidPassword) {
        assertThatThrownBy(() -> UserEntity
                .builder()
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(invalidPassword)
                .build())
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessageContaining(EXPECTED_PASSWORD_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {
            "", " ", "user name",
            "login@", "@login", "lo!gin",
            "login/user", "user\\name", "user:name",
            "user;name", "user,name", "user?name",
            "user=name", "user+name", "user*name",
            "user(name)", "user[name]", "user{name}",
            "user<name>", "user|name", "user&name",
            "user%name", "user#name", "user~name",
            "user`name", "user\"name", "user'name",
            "user\tname", "user\nname", "usuário"
    })
    void shouldRaiseExceptionWhenLoginIsInvalid(String invalidLogin) {
        assertThatThrownBy(() -> UserEntity
                .builder()
                .email(VALID_EMAIL)
                .login(invalidLogin)
                .password(VALID_PASSWORD)
                .build())
                .isInstanceOf(InvalidLoginException.class)
                .hasMessageContaining(EXPECTED_LOGIN_ERROR_MESSAGE);
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"invalid.@mail.com"})
    void shouldRaiseExceptionWhenEmailIsInvalid(String invalidEmail) {
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(false);
        assertThatThrownBy(() -> UserEntity
                .builder()
                .email(invalidEmail)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build())
                .isInstanceOf(InvalidEmailException.class)
                .hasMessageContaining(EXPECTED_EMAIl_ERROR_MESSAGE);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }
}
