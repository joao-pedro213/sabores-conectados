package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.Restaurant;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.RestaurantGateway;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RetrieveRestaurantUseCaseTest {
    @Mock
    private RestaurantGateway mockRestaurantGateway;

    @InjectMocks
    private RetrieveRestaurantByIdUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final UUID RESTAURANT_ID = UUID.randomUUID();

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

    private static Map<String, Object> getOwnerSampleData() {
        Map<String, Object> ownerSampleData = new LinkedHashMap<>();
        ownerSampleData.put("id", UUID.randomUUID());
        ownerSampleData.put("name", "Marcos");
        ownerSampleData.put("userType", UserType.RESTAURANT_OWNER.getValue());
        ownerSampleData.put("email", "marcos@mail.com");
        ownerSampleData.put("login", "marcos635");
        ownerSampleData.put("password", "09231s121!G");
        ownerSampleData.put("address", "82495 Xavier Keys, Emersonburgh, KS 65336-8213");
        ownerSampleData.put("lastUpdated", LocalDateTime.now());
        return ownerSampleData;
    }

    @Test
    @DisplayName("Should find a Restaurant if it exists in the database")
    void shouldFindRestaurantById() {
        // Given
        final Restaurant foundRestaurant = Restaurant
                .builder()
                .id(RESTAURANT_ID)
                .owner(this.userObjectMother.createSampleUser(getOwnerSampleData()))
                .build();

        when(this.mockRestaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.of(foundRestaurant));

        // When
        final Restaurant restaurant = this.useCase.execute(RESTAURANT_ID);

        // Then
        assertThat(restaurant).isNotNull().isEqualTo(foundRestaurant);
        verify(this.mockRestaurantGateway, times(1)).findById(RESTAURANT_ID);
    }

    @Test
    @DisplayName("should throw EntityNotFoundException when the restaurant is not found in the database")
    void shouldThrowEntityNotFoundException() {
        // Given
        when(this.mockRestaurantGateway.findById(RESTAURANT_ID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(RESTAURANT_ID)).isInstanceOf(EntityNotFoundException.class);
        verify(this.mockRestaurantGateway, times(1)).findById(RESTAURANT_ID);
    }
}
