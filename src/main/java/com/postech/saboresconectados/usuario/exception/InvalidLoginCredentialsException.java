package com.postech.saboresconectados.usuario.exception;

public class InvalidLoginCredentialsException extends RuntimeException {
    public InvalidLoginCredentialsException() {
        super("O usuário e/ou a senha são inválidos");
    }
}
