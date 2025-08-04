package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class RetrieveUserByIdUseCase {
    private final UserGateway userGateway;

    public static RetrieveUserByIdUseCase create(UserGateway userGateway) {
        return new RetrieveUserByIdUseCase(userGateway);
    }

    public User execute(UUID id) {
        return this.userGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
    }
}
