package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public static CreateUserUseCase build(UserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    public UserEntity execute(UserEntity userEntity) {
        Optional<UserEntity> foundUser = this.userGateway.findByLogin(userEntity.getLogin());
        if (foundUser.isPresent()) {
            throw new EntityAlreadyExistsException("User");
        }
        return this.userGateway.save(userEntity);
    }
}
