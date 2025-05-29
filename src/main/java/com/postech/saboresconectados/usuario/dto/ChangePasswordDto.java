package com.postech.saboresconectados.usuario.dto;

import com.postech.saboresconectados.usuario.controller.UsuarioValidationPatterns;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordDto {

    private String senhaAtual;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = UsuarioValidationPatterns.PASSWORD_PATTERN,
            message = UsuarioValidationPatterns.INVALID_PASSWORD_PATTERN_MESSAGE)
    private String senhaNova;

}
