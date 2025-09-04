package com.postech.saboresconectados.core.restaurant.domain.usecase;

import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.restaurant.gateways.RestaurantGateway;
import com.postech.saboresconectados.core.restaurant.domain.entity.RestaurantEntity;
import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
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

class CreateRestaurantEntityUseCaseTest {
    @Mock
    private RestaurantGateway mockRestaurantGateway;

    @InjectMocks
    private CreateRestaurantUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private static final String VALID_EMAIL = "gus@domain.com";

    private static final String VALID_LOGIN = "gus.fring";

    private static final String VALID_PASSWORD = "0Wn3r!P4$S";

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
    @DisplayName("Should create a new Restaurant if it doesn't exist in the database yet")
    void shouldCreateRestaurant() {
        // Given
        final UserEntity owner = UserEntity
                .builder()
                .userType(UserType.RESTAURANT_OWNER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();
        final RestaurantEntity newRestaurantEntity = RestaurantEntity.builder().name("Los Pollos Hermanos").owner(owner).build();
        final RestaurantEntity createdRestaurantEntity = newRestaurantEntity.toBuilder().build();
        when(this.mockRestaurantGateway.findByName(newRestaurantEntity.getName())).thenReturn(Optional.empty());
        when(this.mockRestaurantGateway.save(newRestaurantEntity)).thenReturn(createdRestaurantEntity);

        // When
        final RestaurantEntity restaurantEntity = this.useCase.execute(newRestaurantEntity);

        // Then
        assertThat(restaurantEntity).isNotNull().isEqualTo(createdRestaurantEntity);
        verify(this.mockRestaurantGateway, times(1)).findByName(newRestaurantEntity.getName());
        verify(this.mockRestaurantGateway, times(1)).save(newRestaurantEntity);
    }

    @Test
    @DisplayName("should throw a EntityAlreadyExistsException when the new restaurant is found in the database before its creation")
    void shouldThrowEntityAlreadyExist() {
        // Given
        final UserEntity owner = UserEntity
                .builder()
                .userType(UserType.RESTAURANT_OWNER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();
        final RestaurantEntity newRestaurantEntity = RestaurantEntity.builder().name("Los Pollos Hermanos").owner(owner).build();
        when(this.mockRestaurantGateway.findByName(newRestaurantEntity.getName())).thenReturn(Optional.of(newRestaurantEntity));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(newRestaurantEntity))
                .isInstanceOf(EntityAlreadyExistsException.class);
        verify(this.mockRestaurantGateway, times(1)).findByName(newRestaurantEntity.getName());
        verify(this.mockRestaurantGateway, times(0)).save(newRestaurantEntity);
    }
}