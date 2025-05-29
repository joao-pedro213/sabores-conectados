package com.postech.saboresconectados.exception;

import com.postech.saboresconectados.exception.dto.ExceptionDto;
import com.postech.saboresconectados.usuario.exception.InvalidCurrentPasswordException;
import com.postech.saboresconectados.usuario.exception.InvalidLoginCredentialsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDto>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<ExceptionDto> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map((error) -> {
                    return ExceptionDto
                            .builder()
                            .erro("O valor do campo '" + error.getField() + "' é inválido")
                            .detalhes(error.getDefaultMessage())
                            .build();
                })
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException exception) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(
                        ExceptionDto
                                .builder()
                                .erro("Violação de chave única")
                                .detalhes("O recurso já existe no banco de dados")
                                .build());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleResourceNotFoundException(ResourceNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ExceptionDto
                                .builder()
                                .erro("Recurso não econtrado")
                                .detalhes(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(InvalidCurrentPasswordException.class)
    public ResponseEntity<ExceptionDto> handleInvalidOldPasswordException(InvalidCurrentPasswordException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionDto
                                .builder()
                                .erro("Senha incorreta")
                                .detalhes(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(InvalidLoginCredentialsException.class)
    public ResponseEntity<ExceptionDto> handleInvalidLoginCredentialsException(
            InvalidLoginCredentialsException exception) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ExceptionDto
                                .builder()
                                .erro("Credenciais inválidas")
                                .detalhes(exception.getMessage())
                                .build());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionDto> handleGenericRuntimeException(RuntimeException exception) {
        log.error("Um erro inesperado: {}", exception.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ExceptionDto
                                .builder()
                                .erro("Erro desconhecido")
                                .detalhes("Verifique os logs da aplicação para mais detalhes")
                                .build());
    }

}
