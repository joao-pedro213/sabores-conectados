package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.helpers.UserObjectMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserGatewayTest {

    @Mock
    private UserDataSource dataSource;

    @InjectMocks
    private UserGateway gateway;

    private Map<String, Object> userSampleData;

    private final UserObjectMother userObjectMother = new UserObjectMother();

    private static final UUID ID = UUID.randomUUID();

    private static final LocalDateTime LAST_UPDATED = LocalDateTime.now();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.userSampleData = new LinkedHashMap<>();
        this.userSampleData.put("name", "Luan");
        this.userSampleData.put("userType", UserType.RESTAURANT_OWNER.getValue());
        this.userSampleData.put("email", "luan@restaurant.com");
        this.userSampleData.put("login", "luan.restaurant");
        this.userSampleData.put("password", "AKgC12!a!G");
        this.userSampleData.put("address", "99897 Johnson Mountain, Reginaldmouth, CO 46370-9570");
    }

    @Test
    void shouldSaveUser() {
        // Given
        final User userToSave = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        final UserDto saveddUserDto = this.userObjectMother
                .createSampleUserDto(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        when(this.dataSource.save(any(UserDto.class))).thenReturn(saveddUserDto);

        // When
        final User savedUser = this.gateway.save(userToSave);

        // Then
        final ArgumentCaptor<UserDto> argument = ArgumentCaptor.forClass(UserDto.class);
        verify(this.dataSource, times(1)).save(argument.capture());
        final UserDto capturedUserDto = argument.getValue();
        final UserDto expectedUserDto = saveddUserDto.toBuilder().build();
        assertThat(capturedUserDto)
                .usingRecursiveComparison()
                .isEqualTo(expectedUserDto);
        assertThat(savedUser).isNotNull();
        final User expectedUpdatedUser = userToSave.toBuilder().build();
        assertThat(savedUser)
                .usingRecursiveComparison()
                .isEqualTo(expectedUpdatedUser);
    }

    @Test
    void shouldFindUserById() {
        // Given
        final UserDto foundUserDto = this.userObjectMother
                .createSampleUserDto(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        when(this.dataSource.findById(ID)).thenReturn(Optional.of(foundUserDto));

        // When
        Optional<User> foundUser = this.gateway.findById(ID);

        // Then
        verify(this.dataSource, times(1)).findById(ID);
        assertThat(foundUser).isPresent();
        final User expectedFoundUser = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        assertThat(foundUser.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedFoundUser);
    }

    @Test
    void shouldFindUserByIdWhenDataSourceReturnsEmpty() {
        // Given
        when(this.dataSource.findById(ID)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = this.gateway.findById(ID);

        // Then
        verify(this.dataSource, times(1)).findById(ID);
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void shouldFindUserByLogin() {
        // Given
        final String login = this.userSampleData.get("login").toString();
        final UserDto foundUserDto = this.userObjectMother
                .createSampleUserDto(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        when(this.dataSource.findByLogin(login)).thenReturn(Optional.of(foundUserDto));

        // When
        Optional<User> foundUser = this.gateway.findByLogin(login);

        // Then
        verify(this.dataSource, times(1)).findByLogin(login);
        assertThat(foundUser).isPresent();
        final User expectedFoundUser = this.userObjectMother
                .createSampleUser(this.userSampleData)
                .toBuilder()
                .id(ID)
                .lastUpdated(LAST_UPDATED)
                .build();
        assertThat(foundUser.get())
                .usingRecursiveComparison()
                .isEqualTo(expectedFoundUser);
    }

    @Test
    void shouldFindUserByLoginWhenDataSourceReturnsEmpty() {
        // Given
        final String login = this.userSampleData.get("login").toString();
        when(this.dataSource.findByLogin(login)).thenReturn(Optional.empty());

        // When
        Optional<User> foundUser = this.gateway.findByLogin(login);

        // Then
        verify(this.dataSource, times(1)).findByLogin(login);
        assertThat(foundUser).isNotPresent();
    }

    @Test
    void shouldDeleteByIdUserById() {
        // When
        this.gateway.deleteById(ID);

        // Then
        verify(this.dataSource, times(1)).deleteById(ID);
    }
}
