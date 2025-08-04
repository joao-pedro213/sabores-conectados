package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityPresenterTest {
    private UserPresenter presenter;

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private Map<String, Object> userSampleData;

    @BeforeEach
    void setUp() {
        this.presenter = UserPresenter.create();
        this.userSampleData = new LinkedHashMap<>();
        this.userSampleData.put("name", "Laila");
        this.userSampleData.put("userType", UserType.RESTAURANT_OWNER.getValue());
        this.userSampleData.put("email", "laila@pizza.com");
        this.userSampleData.put("login", "laila21");
        this.userSampleData.put("password", "!@#dMasdij0!G");
        this.userSampleData.put("address", "59747 Hilpert Mountains, West Nedmouth, LA 71306");
    }

    @Test
    void shouldMapDomainToDto() {
        // Given
        final UUID id = UUID.randomUUID();
        final LocalDateTime lastUpdated = LocalDateTime.now();
        final UserEntity userEntity = this.userObjectMother.createSampleUser(this.userSampleData)
                .toBuilder()
                .id(id)
                .lastUpdated(lastUpdated)
                .build();

        // When
        final UserDto userDto = this.presenter.toDto(userEntity);

        // Then
        final UserDto expectedUserDto = this.userObjectMother.createSampleUserDto(this.userSampleData)
                .toBuilder()
                .id(id)
                .lastUpdated(lastUpdated)
                .build();
        assertThat(userDto).usingRecursiveComparison().isEqualTo(expectedUserDto);

    }
}
