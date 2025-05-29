package com.postech.saboresconectados.usuario.controller;

public final class UsuarioValidationPatterns {

    public static final String LOGIN_PATTERN = "^[a-zA-Z0-9._-]{5,50}$";
    public static final String INVALID_LOGIN_PATTERN_MESSAGE = "Login pode conter apenas letras, números, '.', '_' e '-'";
    public static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&+=])(?=\\S+$).{8,}$";
    public static final String INVALID_PASSWORD_PATTERN_MESSAGE = "Senha deve conter ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial";

}
