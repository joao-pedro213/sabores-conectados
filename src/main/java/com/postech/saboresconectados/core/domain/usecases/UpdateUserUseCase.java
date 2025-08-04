package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UpdateUserUseCase {
    private final UserGateway userGateway;

    public static UpdateUserUseCase create(UserGateway userGateway) {
        return new UpdateUserUseCase(userGateway);
    }

    public User execute(UUID id, String name, String email, String address) {
        User foundUser = this.userGateway.findById(id).orElseThrow(() -> new EntityNotFoundException("User"));
        User userWithUpdates = foundUser
                .toBuilder()
                .name(name)
                .email(email)
                .address(address)
                .build();
        return this.userGateway.save(userWithUpdates);
    }
}
