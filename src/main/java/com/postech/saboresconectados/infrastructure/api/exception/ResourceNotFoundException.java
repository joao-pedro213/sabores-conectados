package com.postech.saboresconectados.infrastructure.api.exception;

public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(String id) {
    super(String.format("O recurso não foi econtrado: '%s'", id));
  }

}
