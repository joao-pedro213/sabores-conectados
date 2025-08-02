package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.UserAlreadyExistsException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class CreateUserUseCase {
    private final UserGateway userGateway;

    public static CreateUserUseCase create(UserGateway userGateway) {
        return new CreateUserUseCase(userGateway);
    }

    public User execute(User user) {
        Optional<User> foundUser = this.userGateway.findByLogin(user.getLogin());
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException();
        }
        return this.userGateway.save(user);
    }
}
