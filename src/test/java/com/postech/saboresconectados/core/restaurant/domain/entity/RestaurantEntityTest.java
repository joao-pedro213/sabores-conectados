package com.postech.saboresconectados.core.restaurant.domain.entity;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.restaurant.domain.exception.UserNotRestaurantOwnerException;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

class RestaurantEntityTest {
    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private static final String VALID_EMAIL = "test.user@mail.com";

    private static final String VALID_LOGIN = "test.user";

    private static final String VALID_PASSWORD = "P@sswOrd123!";

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
    @DisplayName("should throw UserNotRestaurantOwnerException when user is not of type RESTAURANT_OWNER")
    void shouldThrowUserNotRestaurantOwnerException() {
        // Given
        UserEntity userEntity = UserEntity
                .builder()
                .userType(UserType.CUSTOMER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();
        assertThatThrownBy(() -> RestaurantEntity.builder().owner(userEntity).build())
                .isInstanceOf(UserNotRestaurantOwnerException.class);
    }
}
