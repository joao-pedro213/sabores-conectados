package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class LoginUserUseCase {
    private UserGateway userGateway;

    public static LoginUserUseCase build(UserGateway userGateway) {
        return new LoginUserUseCase(userGateway);
    }

    public void execute(String login, String password) {
        Optional<UserEntity> foundUser = this.userGateway.findByLogin(login);
        if (foundUser.isEmpty() || !foundUser.get().getPassword().equals(password)) {
            throw new InvalidLoginCredentialsException();
        }
    }
}
