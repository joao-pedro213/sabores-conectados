package com.postech.saboresconectados.core.user.gateway;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.core.user.datasource.UserDataSource;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
public class UserGateway {
    private final UserDataSource dataSource;

    public static UserGateway build(UserDataSource dataSource) {
        return new UserGateway(dataSource);
    }

    public UserEntity save(UserEntity userEntity) {
        UserDto userToSave = this.toDto(userEntity);
        UserDto savedUser = this.dataSource.save(userToSave);
        return this.toDomain(savedUser);
    }

    public Optional<UserEntity> findById(UUID id) {
        Optional<UserDto> foundUser = this.dataSource.findById(id);
        return foundUser.map(this::toDomain);
    }

    public Optional<UserEntity> findByLogin(String login) {
        Optional<UserDto> foundUser = this.dataSource.findByLogin(login);
        return foundUser.map(this::toDomain);
    }

    public void deleteById(UUID id) {
        this.dataSource.deleteById(id);
    }

    private UserDto toDto(UserEntity userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .userType(userEntity.getUserType().getValue())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .address(userEntity.getAddress())
                .lastUpdated(userEntity.getLastUpdated())
                .build();
    }

    private UserEntity toDomain(UserDto userDto) {
        return UserEntity
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
