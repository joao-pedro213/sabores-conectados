package com.postech.saboresconectados.usuario.repository;

import com.postech.saboresconectados.usuario.model.Usuario;

import java.util.Optional;
import java.util.UUID;

public interface UsuarioRepository {

    void create(Usuario usuario);

    Optional<Usuario> findById(UUID id);

    Optional<Usuario> findByLogin(String login);

    Integer update(UUID id, Usuario usuario);

    Integer deleteById(UUID id);

    void changePassword(UUID id, String newPassword);

}
