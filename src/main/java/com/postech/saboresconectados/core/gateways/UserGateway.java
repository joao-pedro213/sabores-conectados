package com.postech.saboresconectados.core.gateways;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserGateway {
    private final UserDataSource dataSource;

    public static UserGateway create(UserDataSource dataSource) {
        return new UserGateway(dataSource);
    }

    public User save(User user) {
        UserDto userToSave = this.toDto(user);
        UserDto savedUser = this.dataSource.save(userToSave);
        return this.toDomain(savedUser);
    }

    public Optional<User> findById(UUID id) {
        Optional<UserDto> foundUser = this.dataSource.findById(id);
        return foundUser.map(this::toDomain);
    }

    public Optional<User> findByLogin(String login) {
        Optional<UserDto> foundUser = this.dataSource.findByLogin(login);
        return foundUser.map(this::toDomain);
    }

    public void deleteById(UUID id) {
        this.dataSource.deleteById(id);
    }

    private UserDto toDto(User user) {
        return UserDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .userType(user.getUserType().getValue())
                .email(user.getEmail())
                .login(user.getLogin())
                .password(user.getPassword())
                .address(user.getAddress())
                .lastUpdated(user.getLastUpdated())
                .build();
    }

    private User toDomain(UserDto userDto) {
        return User
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .userType(UserType.fromValue(userDto.getUserType()))
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .lastUpdated(userDto.getLastUpdated())
                .build();
    }
}
