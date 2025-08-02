package com.postech.saboresconectados.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder(toBuilder = true)
public class UserOutputDto {
    private UUID id;
    private String name;
    private String userType;
    private String email;
    private String login;
    private String address;
    private LocalDateTime lastUpdated;
}
