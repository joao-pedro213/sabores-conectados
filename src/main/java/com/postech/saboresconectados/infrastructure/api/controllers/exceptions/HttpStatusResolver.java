package com.postech.saboresconectados.infrastructure.api.controllers.exceptions;

import com.postech.saboresconectados.core.domain.exceptions.BusinessException;
import com.postech.saboresconectados.core.domain.exceptions.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.domain.exceptions.EntityNotFoundException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidEmailException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidPasswordException;
import com.postech.saboresconectados.core.domain.exceptions.UserNotRestaurantOwnerException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HttpStatusResolver {
    public static HttpStatus resolveHttpStatusForBusinessException(BusinessException exception) {
        return switch (exception) {
            case InvalidEmailException i -> HttpStatus.UNPROCESSABLE_ENTITY;
            case InvalidLoginException i -> HttpStatus.UNPROCESSABLE_ENTITY;
            case InvalidPasswordException i -> HttpStatus.UNPROCESSABLE_ENTITY;
            case InvalidLoginCredentialsException i -> HttpStatus.UNAUTHORIZED;
            case UserNotRestaurantOwnerException i -> HttpStatus.FORBIDDEN;
            case EntityNotFoundException i -> HttpStatus.NOT_FOUND;
            case EntityAlreadyExistsException i -> HttpStatus.CONFLICT;
            default -> HttpStatus.BAD_REQUEST;
        };
    }
}
