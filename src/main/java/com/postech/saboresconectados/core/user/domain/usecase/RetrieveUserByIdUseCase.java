package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RetrieveUserByIdUseCase {
    private final UserGateway userGateway;

    public static RetrieveUserByIdUseCase build(UserGateway userGateway) {
        return new RetrieveUserByIdUseCase(userGateway);
    }

    public UserEntity execute(UUID id) {
        return this.userGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
    }
}
