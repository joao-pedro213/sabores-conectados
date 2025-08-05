package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public static UpdateUserUseCase build(UserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }

    public UserEntity execute(UUID id, String name, String email, String address) {
        UserEntity foundUserEntity = this.userGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
        UserEntity userEntityWithUpdates = foundUserEntity
                .toBuilder()
                .name(name)
                .email(email)
                .address(address)
                .build();
        return this.userGateway.save(userEntityWithUpdates);
    }
}
