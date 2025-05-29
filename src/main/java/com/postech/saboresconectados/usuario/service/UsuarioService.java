package com.postech.saboresconectados.usuario.service;

import com.postech.saboresconectados.exception.ResourceNotFoundException;
import com.postech.saboresconectados.usuario.exception.InvalidCurrentPasswordException;
import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public Usuario create(Usuario usuario) {
        usuario.setId(UUID.randomUUID());
        usuario.setSenha(this.passwordEncoder.encode(usuario.getSenha()));
        usuario.setUltimaAlteracao(OffsetDateTime.now(ZoneOffset.UTC));
        this.usuarioRepository.create(usuario);
        return usuario;
    }

    public Usuario findById(UUID id) {
        return this.usuarioRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(id.toString()));
    }

    public void update(UUID id, Usuario usuario) {
        usuario.setUltimaAlteracao(OffsetDateTime.now(ZoneOffset.UTC));
        Integer numberOfAffectedRows = this.usuarioRepository.update(id, usuario);
        if (numberOfAffectedRows == 0) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

    public void changePassword(UUID id, String currentPassword, String newPassword) {
        Usuario usuario = this.findById(id);
        if (!this.passwordMatches(currentPassword, usuario.getSenha())) {
            throw new InvalidCurrentPasswordException();
        }
        this.usuarioRepository.changePassword(id, this.passwordEncoder.encode(newPassword));
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return this.passwordEncoder.matches(rawPassword, hashedPassword);
    }

    public void deleteById(UUID id) {
        Integer numberOfAffectedRows = this.usuarioRepository.deleteById(id);
        if (numberOfAffectedRows == 0) {
            throw new ResourceNotFoundException(id.toString());
        }
    }

}
