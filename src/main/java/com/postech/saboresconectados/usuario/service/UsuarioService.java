package com.postech.saboresconectados.usuario.service;

import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public Usuario create(Usuario usuario) {
        usuario.setId(UUID.randomUUID());
        usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        usuario.setUltimaAlteracao(OffsetDateTime.now());
        this.usuarioRepository.create(usuario);
        return usuario;
    }

}
