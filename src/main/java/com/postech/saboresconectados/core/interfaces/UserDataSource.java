package com.postech.saboresconectados.core.interfaces;

import com.postech.saboresconectados.core.dtos.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface UserDataSource {
    UserDto save(UserDto userDto);
    Optional<UserDto> findById(UUID id);
    Optional<UserDto> findByLogin(String login);
    void deleteById(UUID id);
}
