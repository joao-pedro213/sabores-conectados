package com.postech.saboresconectados.core.user.presenter;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.dto.UserDto;

import java.util.Random;

public class UserPresenter {
    public static UserPresenter build() {
        return new UserPresenter();
    }

    public UserDto toDto(UserEntity userEntity) {
        return UserDto
                .builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .userType(userEntity.getUserType().getValue())
                .email(userEntity.getEmail())
                .password(this.maskPassword(userEntity.getPassword()))
                .login(userEntity.getLogin())
                .address(userEntity.getAddress())
                .lastUpdated(userEntity.getLastUpdated())
                .build();
    }

    private String maskPassword(String password) {
        Random random = new Random();
        int min = 1;
        int max = 3;
        int multiplier = random.nextInt(max - min + 1) + min;
        return "*".repeat(password.length() * multiplier);
    }
}
