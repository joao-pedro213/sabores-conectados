package com.postech.saboresconectados.infrastructure.data.datamappers;

import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewUserRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateUserRequestDto;
import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static UserModel toUserModel(UserDto userDto) {
        return UserModel
                .builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .userType(userDto.getUserType())
                .email(userDto.getEmail())
                .login(userDto.getLogin())
                .password(userDto.getPassword())
                .address(userDto.getAddress())
                .lastUpdated(userDto.getLastUpdated())
                .build();
    }

    public static UserDto toUserDto(UserModel userModel) {
        return UserDto
                .builder()
                .id(userModel.getId())
                .name(userModel.getName())
                .userType(userModel.getUserType())
                .email(userModel.getEmail())
                .login(userModel.getLogin())
                .password(userModel.getPassword())
                .address(userModel.getAddress())
                .lastUpdated(userModel.getLastUpdated())
                .build();
    }

    public static NewUserDto toNewUserDto(NewUserRequestDto newUserRequestDto) {
        return NewUserDto
                .builder()
                .name(newUserRequestDto.getName())
                .userType(newUserRequestDto.getUserType())
                .email(newUserRequestDto.getEmail())
                .login(newUserRequestDto.getLogin())
                .password(newUserRequestDto.getPassword())
                .address(newUserRequestDto.getAddress())
                .build();
    }

    public static UpdateUserDto toUpdateUserDto(UpdateUserRequestDto updateUserRequestDto) {
        return UpdateUserDto
                .builder()
                .name(updateUserRequestDto.getName())
                .email(updateUserRequestDto.getEmail())
                .address(updateUserRequestDto.getAddress())
                .build();
    }
}
