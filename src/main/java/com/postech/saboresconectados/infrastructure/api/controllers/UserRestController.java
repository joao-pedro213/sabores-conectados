package com.postech.saboresconectados.infrastructure.api.controllers;

import com.postech.saboresconectados.core.controller.UserController;
import com.postech.saboresconectados.core.dtos.NewUserDto;
import com.postech.saboresconectados.core.dtos.UpdateUserDto;
import com.postech.saboresconectados.core.dtos.UserDto;
import com.postech.saboresconectados.infrastructure.api.dtos.ChangeUserPasswordRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.LoginUserRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.NewUserRequestDto;
import com.postech.saboresconectados.infrastructure.api.dtos.UpdateUserRequestDto;
import com.postech.saboresconectados.infrastructure.data.UserDataSourceJpa;
import com.postech.saboresconectados.infrastructure.data.datamappers.UserMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserRestController {
    private UserDataSourceJpa userDataSourceJpa;

    @PostMapping
    public ResponseEntity<UserDto> create(@Valid @RequestBody NewUserRequestDto requestDto) {
        NewUserDto newUserDto = UserMapper.toNewUserDto(requestDto);
        UserController userController = UserController.create(this.userDataSourceJpa);
        UserDto userDto = userController.createUser(newUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> retrieve(@PathVariable UUID id) {
        UserController userController = UserController.create(this.userDataSourceJpa);
        UserDto userDto = userController.retrieveUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateUserRequestDto requestDto) {
        UpdateUserDto updateUserDto = UserMapper.toUpdateUserDto(requestDto);
        UserController userController = UserController.create(this.userDataSourceJpa);
        UserDto userDto = userController.updateUser(id, updateUserDto);
        return ResponseEntity.status(HttpStatus.OK).body(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        UserController userController = UserController.create(this.userDataSourceJpa);
        userController.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Valid @RequestBody LoginUserRequestDto requestDto) {
        UserController userController = UserController.create(this.userDataSourceJpa);
        userController.loginUser(requestDto.getLogin(), requestDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangeUserPasswordRequestDto requestDto) {
        UserController userController = UserController.create(this.userDataSourceJpa);
        userController.changeUserPassword(
                requestDto.getLogin(),
                requestDto.getOldPassword(),
                requestDto.getNewPassword());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
