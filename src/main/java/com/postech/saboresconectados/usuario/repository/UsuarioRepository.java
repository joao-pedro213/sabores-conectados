package com.postech.saboresconectados.usuario.repository;

import com.postech.saboresconectados.usuario.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Integer criar(Usuario usuario);

    Optional<Usuario> buscarPorId(UUID id);

    int atualizar(Usuario usuario);

    int remover(UUID id);

}
