package com.postech.saboresconectados.core.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateUserDto {
    private String name;
    private String email;
    private String address;
}
