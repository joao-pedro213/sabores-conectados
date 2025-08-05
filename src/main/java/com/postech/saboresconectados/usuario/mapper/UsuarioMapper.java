package com.postech.saboresconectados.usuario.mapper;

import com.postech.saboresconectados.usuario.dto.NewUsuarioRequestDto;
import com.postech.saboresconectados.usuario.dto.UpdateUsuarioRequestDto;
import com.postech.saboresconectados.usuario.dto.UsuarioResponseDto;
import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.model.enumerator.TipoUsuario;

public class UsuarioMapper {

    public static Usuario newUsuarioRequestDtoToUsuario(NewUsuarioRequestDto newUsuarioRequestDto) {
        return Usuario
                .builder()
                .nome(newUsuarioRequestDto.getNome())
                .tipoUsuario(TipoUsuario.fromValue(newUsuarioRequestDto.getTipoUsuario()))
                .email(newUsuarioRequestDto.getEmail())
                .login(newUsuarioRequestDto.getLogin())
                .senha(newUsuarioRequestDto.getSenha())
                .endereco(newUsuarioRequestDto.getEndereco())
                .build();
    }

    public static Usuario updateUsuarioRequestDtoToUsuario(UpdateUsuarioRequestDto updateUsuarioRequestDto) {
        return Usuario
                .builder()
                .nome(updateUsuarioRequestDto.getNome())
                .email(updateUsuarioRequestDto.getEmail())
                .endereco(updateUsuarioRequestDto.getEndereco())
                .build();
    }

    public static UsuarioResponseDto usuarioToUsuarioResponseDto(Usuario usuario) {
        return UsuarioResponseDto
                .builder()
                .id(usuario.getId())
                .nome(usuario.getNome())
                .tipoUsuario(usuario.getTipoUsuario().getValue())
                .email(usuario.getEmail())
                .login(usuario.getLogin())
                .endereco(usuario.getEndereco())
                .ultimaAlteracao(usuario.getUltimaAlteracao())
                .build();
    }

}
