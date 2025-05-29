package com.postech.saboresconectados.usuario.dto;

import com.postech.saboresconectados.usuario.controller.UsuarioValidationPatterns;
import com.postech.saboresconectados.usuario.model.enumerator.TipoUsuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewUsuarioRequestDto {

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotNull(message = "Tipo de usuário não pode ser nulo")
    private TipoUsuario tipoUsuario;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser um formato válido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    private String email;

    @NotBlank(message = "Login não pode estar em branco")
    @Size(min = 5, max = 50, message = "Login deve ter entre 5 e 50 caracteres")
    @Pattern(
            regexp = UsuarioValidationPatterns.LOGIN_PATTERN,
            message = UsuarioValidationPatterns.INVALID_LOGIN_PATTERN_MESSAGE)
    private String login;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min = 8, message = "Senha deve ter no mínimo 8 caracteres")
    @Pattern(
            regexp = UsuarioValidationPatterns.PASSWORD_PATTERN,
            message = UsuarioValidationPatterns.INVALID_PASSWORD_PATTERN_MESSAGE)
    private String senha;

    @NotBlank(message = "Endereço não pode estar em branco")
    @Size(max = 255, message = "Endereço não pode exceder 255 caracteres")
    private String endereco;

}
