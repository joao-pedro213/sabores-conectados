package com.postech.saboresconectados.usuario.model;

import com.postech.saboresconectados.usuario.model.enumerator.TipoUsuario;
import lombok.Builder;
import lombok.Data;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
public class Usuario {

    private UUID id;

    private String nome;

    private TipoUsuario tipoUsuario;

    private String email;

    private String login;

    private String senha;

    private String endereco;

    private OffsetDateTime ultimaAlteracao;

}
