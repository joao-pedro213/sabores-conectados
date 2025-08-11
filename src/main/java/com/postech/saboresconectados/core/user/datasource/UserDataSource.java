package com.postech.saboresconectados.core.user.datasource;

import com.postech.saboresconectados.core.user.dto.UserDto;

import java.util.Optional;
import java.util.UUID;

public interface UserDataSource {
    UserDto save(UserDto userDto);

    Optional<UserDto> findById(UUID id);

    Optional<UserDto> findByLogin(String login);

    void deleteById(UUID id);
}
