package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.User;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.usecases.ChangeUserPasswordUseCase;
import com.postech.saboresconectados.core.domain.usecases.CreateUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.LoginUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateUserUseCase;
import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserOutputDto;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.UserPresenter;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserController {
    private final UserDataSource userDataSource;

    public static UserController create(UserDataSource userDataSource) {
        return new UserController(userDataSource);
    }

    public UserOutputDto createUser(NewUserDto newUserDto) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        CreateUserUseCase useCase = CreateUserUseCase.create(userGateway);
        User newUser = useCase.execute(this.toDomain(newUserDto));
        UserPresenter presenter = UserPresenter.create();
        return presenter.toDto(newUser);
    }

    public UserOutputDto retrieveUserById(UUID id) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        RetrieveUserByIdUseCase useCase = RetrieveUserByIdUseCase.create(userGateway);
        User foundUser = useCase.execute(id);
        UserPresenter presenter = UserPresenter.create();
        return presenter.toDto(foundUser);
    }

    public UserOutputDto updateUser(UUID id, UpdateUserDto updateUserDto) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        UpdateUserUseCase useCase = UpdateUserUseCase.create(userGateway);
        User updatedUser = useCase
                .execute(
                        id,
                        updateUserDto.getName(),
                        updateUserDto.getEmail(),
                        updateUserDto.getAddress());
        UserPresenter presenter = UserPresenter.create();
        return presenter.toDto(updatedUser);
    }

    public void deleteUserById(UUID id) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        DeleteUserByIdUseCase useCase = DeleteUserByIdUseCase.create(userGateway);
        useCase.execute(id);
    }

    public void loginUser(String login, String password) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        LoginUserUseCase useCase = LoginUserUseCase.create(userGateway);
        useCase.execute(login, password);
    }

    public void changeUserPassword(String login, String oldPassword, String newPassword) {
        UserGateway userGateway = UserGateway.create(this.userDataSource);
        ChangeUserPasswordUseCase useCase = ChangeUserPasswordUseCase.create(userGateway);
        useCase.execute(login, oldPassword, newPassword);
    }

    private User toDomain(NewUserDto newUserDto) {
        return User
                .builder()
                .name(newUserDto.getName())
                .userType(UserType.fromValue(newUserDto.getUserType()))
                .email(newUserDto.getEmail())
                .login(newUserDto.getLogin())
                .password(newUserDto.getPassword())
                .address(newUserDto.getAddress())
                .build();
    }
}
