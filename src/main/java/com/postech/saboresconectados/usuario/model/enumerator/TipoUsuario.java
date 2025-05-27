package com.postech.saboresconectados.usuario.model.enumerator;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TipoUsuario {

    DONO_DE_RESTAURANTE("Dono de Restaurante"),
    CLIENTE("Cliente");

    private final String value;

    TipoUsuario(String value) {
        this.value = value;
    }

    public static TipoUsuario fromValue(String value) {
        return Arrays
                .stream(TipoUsuario.values())
                .filter((tipo) -> tipo.getValue().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("O valor de TipoUsuario informado não é válido"));
    }

}
