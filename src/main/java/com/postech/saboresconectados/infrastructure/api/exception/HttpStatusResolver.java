package com.postech.saboresconectados.infrastructure.api.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;
import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidEmailException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidPasswordException;
import com.postech.saboresconectados.core.restaurant.domain.exception.UserNotRestaurantOwnerException;
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
