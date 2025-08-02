package com.postech.saboresconectados.infrastructure.api.controllers.exceptions;

import com.postech.saboresconectados.core.domain.exceptions.BusinessException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidEmailException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginCredentialsException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidLoginException;
import com.postech.saboresconectados.core.domain.exceptions.InvalidPasswordException;
import com.postech.saboresconectados.core.domain.exceptions.UserAlreadyExistsException;
import com.postech.saboresconectados.core.domain.exceptions.UserNotFoundException;
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
    void shouldReturnConflictForUserAlreadyExistException() {
        // Given
        BusinessException exception = new UserAlreadyExistsException();

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

    @Test
    void shouldReturnNotFoundForUserNotFoundException() {
        // Given
        BusinessException exception = new UserNotFoundException();

        // When
        HttpStatus httpStatus = HttpStatusResolver.resolveHttpStatusForBusinessException(exception);

        // Then
        assertThat(httpStatus).isEqualTo(HttpStatus.NOT_FOUND);
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
}
