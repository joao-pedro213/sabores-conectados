package com.postech.saboresconectados.usuario.controller;

import com.postech.saboresconectados.usuario.dto.NewUsuarioRequestDto;
import com.postech.saboresconectados.usuario.dto.UpdateUsuarioRequestDto;
import com.postech.saboresconectados.usuario.dto.UsuarioResponseDto;
import com.postech.saboresconectados.usuario.mapper.UsuarioMapper;
import com.postech.saboresconectados.usuario.model.Usuario;
import com.postech.saboresconectados.usuario.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/usuario")
@AllArgsConstructor
public class UsuarioController {

    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDto> create(@RequestBody @Valid NewUsuarioRequestDto newUsuarioRequestDto) {
        Usuario usuario = UsuarioMapper.newUsuarioRequestDtoToUsuario(newUsuarioRequestDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(UsuarioMapper.usuarioToUsuarioResponseDto(this.usuarioService.create(usuario)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestBody @Valid UpdateUsuarioRequestDto updateUsuarioRequestDto) {
        Usuario usuario = UsuarioMapper.updateUsuarioRequestDtoToUsuario(updateUsuarioRequestDto);
        this.usuarioService.update(UUID.fromString(id), usuario);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDto> findById(@PathVariable String id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(UsuarioMapper.usuarioToUsuarioResponseDto(this.usuarioService.findById(UUID.fromString(id))));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        this.usuarioService.deleteById(UUID.fromString(id));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
