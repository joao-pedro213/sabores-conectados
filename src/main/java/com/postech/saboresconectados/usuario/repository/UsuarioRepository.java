package com.postech.saboresconectados.usuario.repository;

import com.postech.saboresconectados.usuario.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    Integer create(Usuario usuario);

    Optional<Usuario> findById(UUID id);

    Integer update(UUID id, Usuario usuario);

    Integer deleteById(UUID id);

}
