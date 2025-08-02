package com.postech.saboresconectados.infrastructure.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginUserRequestDto {
    @NotBlank
    private String login;

    @NotBlank
    private String password;
}
