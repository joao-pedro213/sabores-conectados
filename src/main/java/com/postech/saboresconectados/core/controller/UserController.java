package com.postech.saboresconectados.core.controller;

import com.postech.saboresconectados.core.domain.entities.UserEntity;
import com.postech.saboresconectados.core.domain.entities.enumerators.UserType;
import com.postech.saboresconectados.core.domain.usecases.ChangeUserPasswordUseCase;
import com.postech.saboresconectados.core.domain.usecases.CreateUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.DeleteUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.LoginUserUseCase;
import com.postech.saboresconectados.core.domain.usecases.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.domain.usecases.UpdateUserUseCase;
import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.core.gateways.UserGateway;
import com.postech.saboresconectados.core.interfaces.UserDataSource;
import com.postech.saboresconectados.core.presenters.UserPresenter;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class UserController {
    private final UserDataSource userDataSource;

    public static UserController build(UserDataSource userDataSource) {
        return new UserController(userDataSource);
    }

    public UserDto createUser(NewUserDto newUserDto) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        CreateUserUseCase useCase = CreateUserUseCase.build(userGateway);
        UserEntity newUserEntity = useCase.execute(this.toDomain(newUserDto));
        UserPresenter presenter = UserPresenter.build();
        return presenter.toDto(newUserEntity);
    }

    public UserDto retrieveUserById(UUID id) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        RetrieveUserByIdUseCase useCase = RetrieveUserByIdUseCase.build(userGateway);
        UserEntity foundUserEntity = useCase.execute(id);
        UserPresenter presenter = UserPresenter.build();
        return presenter.toDto(foundUserEntity);
    }

    public UserDto updateUser(UUID id, UpdateUserDto updateUserDto) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        UpdateUserUseCase useCase = UpdateUserUseCase.build(userGateway);
        UserEntity updatedUserEntity = useCase
                .execute(
                        id,
                        updateUserDto.getName(),
                        updateUserDto.getEmail(),
                        updateUserDto.getAddress());
        UserPresenter presenter = UserPresenter.build();
        return presenter.toDto(updatedUserEntity);
    }

    public void deleteUserById(UUID id) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        DeleteUserByIdUseCase useCase = DeleteUserByIdUseCase.build(userGateway);
        useCase.execute(id);
    }

    public void loginUser(String login, String password) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        LoginUserUseCase useCase = LoginUserUseCase.build(userGateway);
        useCase.execute(login, password);
    }

    public void changeUserPassword(String login, String oldPassword, String newPassword) {
        UserGateway userGateway = UserGateway.build(this.userDataSource);
        ChangeUserPasswordUseCase useCase = ChangeUserPasswordUseCase.build(userGateway);
        useCase.execute(login, oldPassword, newPassword);
    }

    private UserEntity toDomain(NewUserDto newUserDto) {
        return UserEntity
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
