package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.dtos.UserDto;

public class UserPresenter {
    public static UserPresenter create() {
        return new UserPresenter();
    }

    public UserDto toDto(UserEntity userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .userType(userEntity.getUserType().getValue())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .login(userEntity.getLogin())
                .address(userEntity.getAddress())
                .lastUpdated(userEntity.getLastUpdated())
                .build();
    }
}
