package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteUserByIdUseCase {
    private final UserGateway userGateway;

    public static DeleteUserByIdUseCase create(UserGateway userGateway) {
        return new DeleteUserByIdUseCase(userGateway);
    }

    public void execute(UUID id) {
        this.userGateway.deleteById(id);
    }
}
