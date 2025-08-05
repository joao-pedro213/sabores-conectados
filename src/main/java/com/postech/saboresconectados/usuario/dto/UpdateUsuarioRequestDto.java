package com.postech.saboresconectados.usuario.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUsuarioRequestDto {

    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    private String nome;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "Email deve ser um formato válido")
    @Size(max = 255, message = "Email não pode exceder 255 caracteres")
    private String email;

    @NotBlank(message = "Endereço não pode estar em branco")
    @Size(max = 255, message = "Endereço não pode exceder 255 caracteres")
    private String endereco;

}
