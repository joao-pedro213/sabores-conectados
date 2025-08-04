package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public static CreateUserUseCase create(UserGateway userGateway) {
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
