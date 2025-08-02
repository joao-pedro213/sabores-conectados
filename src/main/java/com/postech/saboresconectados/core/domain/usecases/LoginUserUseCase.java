package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class LoginUserUseCase {
    private UserGateway userGateway;

    public static LoginUserUseCase create(UserGateway userGateway) {
        return new LoginUserUseCase(userGateway);
    }

    public void execute(String login, String password) {
        Optional<User> foundUser = this.userGateway.findByLogin(login);
        if (foundUser.isEmpty() || !foundUser.get().getPassword().equals(password)) {
            throw new InvalidLoginCredentialsException();
        }
    }
}
