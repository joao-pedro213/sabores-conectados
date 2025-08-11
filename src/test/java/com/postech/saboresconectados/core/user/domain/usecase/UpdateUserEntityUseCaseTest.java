package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.apache.commons.validator.routines.EmailValidator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
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

class UpdateUserEntityUseCaseTest {
    @Mock
    private UserGateway mockUserGateway;

    @InjectMocks
    private UpdateUserUseCase useCase;

    @Mock
    private EmailValidator mockEmailValidator;

    private MockedStatic<EmailValidator> mockedStaticEmailValidator;

    private Map<String, Object> userSampleData;

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final UUID ID = UUID.randomUUID();

    private static final String NEW_NAME = "Augusto O.";

    private static final String NEW_EMAIL = "augusto.01@example.com";

    private static final String NEW_ADDRESS = "9673 Bahringer Squares, Port Shylamouth, NE 32824-4680";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockedStaticEmailValidator = mockStatic(EmailValidator.class);
        this.mockedStaticEmailValidator.when(EmailValidator::getInstance).thenReturn(this.mockEmailValidator);
        when(this.mockEmailValidator.isValid(any(String.class))).thenReturn(true);
        this.userSampleData = new LinkedHashMap<>();
        this.userSampleData.put("name", "Augusto");
        this.userSampleData.put("userType", UserType.CUSTOMER.getValue());
        this.userSampleData.put("email", "augusto.o@example.com");
        this.userSampleData.put("login", "augusto.0");
        this.userSampleData.put("password", "al2AGa$%a5");
        this.userSampleData.put("address", "4308 Jeannine Fork, Port Anthonyborough, MN 49333-2561");
    }

    @Test
    @DisplayName("should update a User if it exists in the database")
    void shouldUpdateUser() {
        // Given
        final UserEntity userEntityWithUpdates = UserEntity
                .builder()
                .id(ID)
                .name(NEW_NAME)
                .userType(UserType.RESTAURANT_OWNER)
                .email(NEW_EMAIL)
                .login("updated.augusto")
                .password("uPdt3dPwd!")
                .address(NEW_ADDRESS)
                .lastUpdated(LocalDateTime.now().minusHours(3))
                .build();
        final UserEntity foundUserEntity = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .build();
        when(this.mockUserGateway.findById(ID)).thenReturn(Optional.of(foundUserEntity));
        final UserEntity updatedUserEntity = userEntityWithUpdates.toBuilder().build();
        when(this.mockUserGateway.save(any(UserEntity.class))).thenReturn(updatedUserEntity);

        // When
        final UserEntity userEntity = this.useCase.execute(ID, NEW_NAME, NEW_EMAIL, NEW_ADDRESS);

        // Then
        assertThat(userEntity).isNotNull().isEqualTo(updatedUserEntity);
        verify(this.mockUserGateway, times(1)).findById(ID);
        final ArgumentCaptor<UserEntity> argument = ArgumentCaptor.forClass(UserEntity.class);
        verify(this.mockUserGateway, times(1)).save(argument.capture());
        final UserEntity capturedUserEntity = argument.getValue();
        final UserEntity expectedUserEntity = foundUserEntity
                .toBuilder()
                .name(NEW_NAME)
                .email(NEW_EMAIL)
                .address(NEW_ADDRESS)
                .build();
        assertThat(capturedUserEntity).usingRecursiveComparison().isEqualTo(expectedUserEntity);
    }

    @Test
    @DisplayName("should throw a EntityNotFoundException when the user is not found in the database")
    void shouldThrowEntityNotFoundException() {
        // Given
        final UserEntity userEntityWithUpdates = UserEntity
                .builder()
                .id(ID)
                .email("new.mail@domain.com")
                .login("updated.usr")
                .password("0paLhuad823!")
                .build();
        when(this.mockUserGateway.findById(ID)).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> this.useCase.execute(ID, NEW_NAME, NEW_EMAIL, NEW_ADDRESS))
                .isInstanceOf(EntityNotFoundException.class);
        verify(this.mockUserGateway, times(1)).findById(ID);
        verify(this.mockUserGateway, times(0)).save(userEntityWithUpdates);
    }

    @AfterEach
    void tearDown() {
        if (this.mockedStaticEmailValidator != null) {
            this.mockedStaticEmailValidator.close();
        }
    }
}
