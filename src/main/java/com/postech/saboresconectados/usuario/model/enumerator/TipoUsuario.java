package com.postech.saboresconectados.usuario.model.enumerator;

import lombok.Getter;

@Getter
public enum TipoUsuario {

    DONO_DE_RESTAURANTE("Dono de Restaurante"),
    CLIENTE("Cliente");

    private final String valorTipoUsuario;

    TipoUsuario(String valorTipoUsuario) {
        this.valorTipoUsuario = valorTipoUsuario;
    }

}
