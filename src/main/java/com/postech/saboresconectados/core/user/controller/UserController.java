package com.postech.saboresconectados.core.user.controller;

import com.postech.saboresconectados.core.user.domain.entity.UserEntity;
import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.core.user.domain.usecase.ChangeUserPasswordUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.CreateUserUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.DeleteUserByIdUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.LoginUserUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.RetrieveUserByIdUseCase;
import com.postech.saboresconectados.core.user.domain.usecase.UpdateUserUseCase;
import com.postech.saboresconectados.core.user.dto.NewUserDto;
import com.postech.saboresconectados.core.user.dto.UpdateUserDto;
import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.core.user.gateway.UserGateway;
import com.postech.saboresconectados.core.user.datasource.UserDataSource;
import com.postech.saboresconectados.core.user.presenter.UserPresenter;
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
