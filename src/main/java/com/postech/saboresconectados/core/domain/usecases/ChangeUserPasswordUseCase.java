package com.postech.saboresconectados.core.domain.usecases;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.gateways.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ChangeUserPasswordUseCase {
    private UserGateway userGateway;

    public static ChangeUserPasswordUseCase create(UserGateway userGateway) {
        return new ChangeUserPasswordUseCase(userGateway);
    }

    public void execute(String login, String oldPassword, String newPassword) {
        Optional<User> foundUser = this.userGateway.findByLogin(login);
        if (foundUser.isEmpty() || !foundUser.get().getPassword().equals(oldPassword)) {
            throw new InvalidLoginCredentialsException();
        }
        this.userGateway.save(foundUser.get().toBuilder().password(newPassword).build());
    }

}
