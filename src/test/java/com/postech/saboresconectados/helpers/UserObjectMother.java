package com.postech.saboresconectados.helpers;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserDto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

public class UserObjectMother {

    public UserEntity createSampleUser(Map<String, Object> sampleData) {
        return UserEntity
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .name(sampleData.get("name").toString())
                .userType(UserType.fromValue(sampleData.get("userType").toString()))
                .email(sampleData.get("email").toString())
                .login(sampleData.get("login").toString())
                .password(sampleData.get("password").toString())
                .address(sampleData.get("address").toString())
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }

    public UserDto createSampleUserDto(Map<String, Object> sampleData) {
        return UserDto
                .builder()
                .id(
                        sampleData.get("id") == null
                                ? UUID.randomUUID()
                                : UUID.fromString(sampleData.get("id").toString()))
                .name(sampleData.get("name").toString())
                .userType(sampleData.get("userType").toString())
                .email(sampleData.get("email").toString())
                .login(sampleData.get("login").toString())
                .password(sampleData.get("password").toString())
                .address(sampleData.get("address").toString())
                .lastUpdated(
                        sampleData.get("lastUpdated") == null
                                ? LocalDateTime.now()
                                : LocalDateTime.parse(sampleData.get("lastUpdated").toString()))
                .build();
    }

    public NewUserDto createSampleNewUserDto(Map<String, Object> sampleData) {
        return NewUserDto
                .builder()
                .name(sampleData.get("name").toString())
                .userType(sampleData.get("userType").toString())
                .email(sampleData.get("email").toString())
                .login(sampleData.get("login").toString())
                .password(sampleData.get("password").toString())
                .address(sampleData.get("address").toString())
                .build();
    }

    public UpdateUserDto createSampleUpdateUserDto(Map<String, Object> sampleData) {
        return UpdateUserDto
                .builder()
                .name(sampleData.get("name").toString())
                .email(sampleData.get("email").toString())
                .address(sampleData.get("address").toString())
                .build();
    }
}
