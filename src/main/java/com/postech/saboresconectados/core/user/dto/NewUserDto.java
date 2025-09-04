package com.postech.saboresconectados.core.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewUserDto {
    private String name;
    private String userType;
    private String email;
    private String login;
    private String password;
    private String address;
}
