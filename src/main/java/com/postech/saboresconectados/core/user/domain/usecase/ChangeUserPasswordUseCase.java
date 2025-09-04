package com.postech.saboresconectados.core.user.domain.usecase;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import lombok.AllArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
public class ChangeUserPasswordUseCase {
    private UserGateway userGateway;

    public static ChangeUserPasswordUseCase build(UserGateway userGateway) {
        return new ChangeUserPasswordUseCase(userGateway);
    }

    public void execute(String login, String oldPassword, String newPassword) {
        Optional<UserEntity> foundUser = this.userGateway.findByLogin(login);
        if (foundUser.isEmpty() || !foundUser.get().getPassword().equals(oldPassword)) {
            throw new InvalidLoginCredentialsException();
        }
        this.userGateway.save(foundUser.get().toBuilder().password(newPassword).build());
    }

}
