package com.postech.saboresconectados.usuario.controller;

import com.postech.saboresconectados.usuario.dto.NewUsuarioDto;
import com.postech.saboresconectados.usuario.dto.UsuarioDto;
import com.postech.saboresconectados.usuario.mapper.UsuarioMapper;
import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDto> register(@RequestBody @Valid NewUsuarioDto newUsuarioDto) {
        Usuario newUsuario = UsuarioMapper.newUsuarioDtoToUsuario(newUsuarioDto);
        newUsuario = this.usuarioService.create(newUsuario);
        UsuarioDto usuarioDto = UsuarioMapper.usuarioToUsuarioDto(newUsuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);
    }

}
