package com.postech.saboresconectados.infrastructure.api.exception;

import com.postech.saboresconectados.core.common.exception.BusinessException;
import com.postech.saboresconectados.core.common.exception.EntityAlreadyExistsException;
import com.postech.saboresconectados.core.common.exception.EntityNotFoundException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidEmailException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidLoginException;
import com.postech.saboresconectados.core.user.domain.exception.InvalidPasswordException;
import com.postech.saboresconectados.core.restaurant.domain.exception.UserNotRestaurantOwnerException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

class HttpStatusResolverTest {
    @Test
    void shouldReturnUnprocessableEntityForInvalidEmailException() {
        // Given
        BusinessException exception = new InvalidEmailException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void shouldReturnUnprocessableEntityForInvalidLoginException() {
        // Given
        BusinessException exception = new InvalidLoginException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void shouldReturnUnprocessableEntityForInvalidPasswordException() {
        // Given
        BusinessException exception = new InvalidPasswordException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    void shouldReturnUnauthorizedForInvalidLoginCredentialsException() {
        // Given
        BusinessException exception = new InvalidLoginCredentialsException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void shouldReturnForbiddenForUserNotRestaurantOwnerException() {
        // Given
        BusinessException exception = new UserNotRestaurantOwnerException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void shouldReturnNotFoundForEntityNotFoundException() {
        // Given
        BusinessException exception = new EntityNotFoundException("Test");

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldReturnConflictForEntityAlreadyExistException() {
        // Given
        BusinessException exception = new EntityAlreadyExistsException("Test");

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.CONFLICT);
    }

    @Test
    void shouldReturnBadRequestForGenericBusinessException() {
        // Given
        BusinessException exception = new BusinessException("A generic business error occurred");

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
