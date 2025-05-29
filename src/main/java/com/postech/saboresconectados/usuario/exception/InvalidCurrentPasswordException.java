package com.postech.saboresconectados.usuario.exception;

public class InvalidCurrentPasswordException extends RuntimeException {
    public InvalidCurrentPasswordException() {
        super("A senha atual informada não está correta");
    }
}
