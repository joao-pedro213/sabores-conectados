package com.postech.saboresconectados.core.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserDto {
    private UUID id;
    private String name;
    private String userType;
    private String email;
    private String login;
    private String password;
    private String address;
    private LocalDateTime lastUpdated;
}
