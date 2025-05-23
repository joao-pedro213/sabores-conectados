package com.postech.saboresconectados.exception;

import com.postech.saboresconectados.exception.dto.ExceptionDto;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDto>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException exception) {
        List<ExceptionDto> errors = exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map((error) -> {
                    ExceptionDto exceptionDto = new ExceptionDto();
                    exceptionDto.setErro("O valor do campo '" + error.getField() + "' é inválido");
                    exceptionDto.setDetalhes(error.getDefaultMessage());
                    return exceptionDto;
                })
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ExceptionDto> handleDuplicateKeyException(DuplicateKeyException exception) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setErro("Violação de chave única");
        exceptionDto.setDetalhes("O recurso já existe no banco de dados");
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(exceptionDto);
    }

}
