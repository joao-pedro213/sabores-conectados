package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.exceptions.RestaurantAlreadyExistsException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
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

class CreateRestaurantUseCaseTest {
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
        final User owner = User
                .builder()
                .userType(UserType.RESTAURANT_OWNER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();
        final Restaurant newRestaurant = Restaurant.builder().name("Los Pollos Hermanos").owner(owner).build();
        final Restaurant createdRestaurant = newRestaurant.toBuilder().build();
        when(this.mockRestaurantGateway.findByName(newRestaurant.getName())).thenReturn(Optional.empty());
        when(this.mockRestaurantGateway.save(newRestaurant)).thenReturn(createdRestaurant);

        // When
        final Restaurant restaurant = this.useCase.execute(newRestaurant);

        // Then
        assertThat(restaurant).isNotNull().isEqualTo(createdRestaurant);
        verify(this.mockRestaurantGateway, times(1)).findByName(newRestaurant.getName());
        verify(this.mockRestaurantGateway, times(1)).save(newRestaurant);
    }

    @Test
    @DisplayName("should throw a RestaurantAlreadyExistsException when the new restaurant is found in the database before its creation")
    void shouldThrowRestaurantAlreadyExist() {
        // Given
        final User owner = User
                .builder()
                .userType(UserType.RESTAURANT_OWNER)
                .email(VALID_EMAIL)
                .login(VALID_LOGIN)
                .password(VALID_PASSWORD)
                .build();
        final Restaurant newRestaurant = Restaurant.builder().name("Los Pollos Hermanos").owner(owner).build();
        when(this.mockRestaurantGateway.findByName(newRestaurant.getName())).thenReturn(Optional.of(newRestaurant));

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(newRestaurant))
                .isInstanceOf(RestaurantAlreadyExistsException.class);
        verify(this.mockRestaurantGateway, times(1)).findByName(newRestaurant.getName());
        verify(this.mockRestaurantGateway, times(0)).save(newRestaurant);
    }
}