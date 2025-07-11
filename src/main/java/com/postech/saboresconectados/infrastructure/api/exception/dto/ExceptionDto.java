package com.postech.saboresconectados.infrastructure.api.exception.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExceptionDto {

    private String erro;

    private String detalhes;

}
