package com.postech.saboresconectados.infrastructure.user.data;

import com.postech.saboresconectados.core.user.dto.UserDto;
import com.postech.saboresconectados.core.user.datasource.UserDataSource;
import com.postech.saboresconectados.infrastructure.user.data.mapper.UserMapper;
import com.postech.saboresconectados.infrastructure.user.data.model.UserModel;
import com.postech.saboresconectados.infrastructure.user.data.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class UserDataSourceJpa implements UserDataSource {
    private final UserRepository repository;

    @Override
    public UserDto save(UserDto userDto) {
        UserModel userToSave = UserMapper.toUserModel(userDto);
        UserModel savedUser = this.repository.save(userToSave);
        return UserMapper.toUserDto(savedUser);
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
        Optional<UserModel> foundUser = this.repository.findById(id);
        return foundUser.map(UserMapper::toUserDto);
    }

    @Override
    public Optional<UserDto> findByLogin(String login) {
        Optional<UserModel> foundUser = this.repository.findByLogin(login);
        return foundUser.map(UserMapper::toUserDto);
    }

    @Override
    public void deleteById(UUID id) {
        this.repository.deleteById(id);
    }
}
