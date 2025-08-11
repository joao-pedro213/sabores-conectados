package com.postech.saboresconectados.infrastructure.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateUserRequestDto {
    @NotBlank
    private String name;

    @NotBlank
    private String email;

    @NotBlank
    private String address;
}
