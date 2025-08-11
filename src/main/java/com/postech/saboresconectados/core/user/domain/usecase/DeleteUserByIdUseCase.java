package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.gateway.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DeleteUserByIdUseCase {
    private final UserGateway userGateway;

    public static DeleteUserByIdUseCase build(UserGateway userGateway) {
        return new DeleteUserByIdUseCase(userGateway);
    }

    public void execute(UUID id) {
        this.userGateway.deleteById(id);
    }
}
