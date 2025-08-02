package com.postech.saboresconectados.infrastructure.api.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ChangeUserPasswordRequestDto {
    @NotBlank
    private String login;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;
}
