package com.postech.saboresconectados.infrastructure.user.dto;

import com.postech.saboresconectados.core.user.domain.entity.enumerator.UserType;
import com.postech.saboresconectados.infrastructure.api.validator.InEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class NewUserRequestDto {
    @NotBlank
    private String name;

    @InEnum(enumClass = UserType.class)
    private String userType;

    @NotBlank
    private String email;

    @NotBlank
    private String login;

    @NotBlank
    private String password;

    @NotBlank
    private String address;
}
