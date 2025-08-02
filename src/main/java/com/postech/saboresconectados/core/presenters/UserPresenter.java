package com.postech.saboresconectados.core.presenters;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.dtos.UserOutputDto;

public class UserPresenter {
    public static UserPresenter create() {
        return new UserPresenter();
    }

    public UserOutputDto toDto(User user) {
        return UserOutputDto
                .builder()
                .id(user.getId())
                .name(user.getName())
                .userType(user.getUserType().getValue())
                .email(user.getEmail())
                .login(user.getLogin())
                .address(user.getAddress())
                .lastUpdated(user.getLastUpdated())
                .build();
    }
}
