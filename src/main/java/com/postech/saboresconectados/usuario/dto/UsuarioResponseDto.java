package com.postech.saboresconectados.usuario.dto;

import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class UsuarioResponseDto {

    private UUID id;

    private String nome;

    private String tipoUsuario;

    private String email;

    private String login;

    private String endereco;

    private OffsetDateTime ultimaAlteracao;

}
