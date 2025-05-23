package com.postech.saboresconectados.usuario.mapper;

import com.postech.saboresconectados.usuario.dto.NewUsuarioDto;
import com.postech.saboresconectados.usuario.dto.UsuarioDto;
import com.postech.saboresconectados.usuario.model.Usuario;

public class UsuarioMapper {

    public static Usuario newUsuarioDtoToUsuario(NewUsuarioDto newUsuarioDto) {
        Usuario usuario = new Usuario();
        usuario.setNome(newUsuarioDto.getNome());
        usuario.setTipoUsuario(newUsuarioDto.getTipoUsuario());
        usuario.setEmail(newUsuarioDto.getEmail());
        usuario.setLogin(newUsuarioDto.getLogin());
        usuario.setSenha(newUsuarioDto.getSenha());
        usuario.setEndereco(newUsuarioDto.getEndereco());
        return usuario;
    }

    public static UsuarioDto usuarioToUsuarioDto(Usuario usuario) {
        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setId(usuario.getId());
        usuarioDto.setNome(usuario.getNome());
        usuarioDto.setTipoUsuario(usuario.getTipoUsuario().getValue());
        usuarioDto.setEmail(usuario.getEmail());
        usuarioDto.setLogin(usuario.getLogin());
        usuarioDto.setEndereco(usuario.getEndereco());
        usuarioDto.setUltimaAlteracao(usuario.getUltimaAlteracao());
        return usuarioDto;
    }

}
