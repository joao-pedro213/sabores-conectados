package com.postech.saboresconectados.infrastructure.data.datamappers;

import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewUserRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateUserRequestDto;
import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class UserEntityMapperTest {
    @Test
    void shouldMapUserDtoToUserModel() {
        // Given
        UserDto userDto = UserDto
                .builder()
                .id(UUID.randomUUID())
                .name("John Doe")
                .userType("Customer")
                .email("john.doe@example.com")
                .login("johndoe")
                .password("securepassword123")
                .address("123 Main St, Anytown")
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        UserModel userModel = UserMapper.toUserModel(userDto);

        // Then
        assertThat(userModel).isNotNull();
        assertThat(userModel).usingRecursiveComparison().isEqualTo(userDto);
    }

    @Test
    void shouldMapUserModelToUserDto() {
        // Given
        UserModel userModel = UserModel.builder()
                .id(UUID.randomUUID())
                .name("Jane Smith")
                .userType("Restaurant Owner")
                .email("jane.smith@example.com")
                .login("janesmith")
                .password("anothersecurepass")
                .address("456 Oak Ave, Otherville")
                .lastUpdated(LocalDateTime.now())
                .build();

        // When
        UserDto userDto = UserMapper.toUserDto(userModel);

        // Then
        assertThat(userDto).isNotNull();
        assertThat(userDto).usingRecursiveComparison().isEqualTo(userModel);
    }

    @Test
    void shouldMapNewUserRequestDtoToNewUserDto() {
        // Given
        NewUserRequestDto newUserRequestDto = NewUserRequestDto.builder()
                .name("Jane Smith")
                .userType("Customer")
                .email("jane.smith@example.com")
                .login("janesmith")
                .password("anothersecurepass")
                .address("456 Oak Ave, Otherville")
                .build();

        // When
        NewUserDto newUserDto = UserMapper.toNewUserDto(newUserRequestDto);

        // Then
        assertThat(newUserDto).isNotNull();
        assertThat(newUserDto).usingRecursiveComparison().isEqualTo(newUserRequestDto);
    }

    @Test
    void shouldMapUpdateUserRequestDtoToUpdateUserDto() {
        // Given
        UpdateUserRequestDto updateUserRequestDto = UpdateUserRequestDto.builder()
                .name("Jane Smith")
                .email("jane.smith@example.com")
                .address("456 Oak Ave, Otherville")
                .build();

        // When
        UpdateUserDto updateUserDto = UserMapper.toUpdateUserDto(updateUserRequestDto);

        // Then
        assertThat(updateUserDto).isNotNull();
        assertThat(updateUserDto).usingRecursiveComparison().isEqualTo(updateUserRequestDto);
    }
}
