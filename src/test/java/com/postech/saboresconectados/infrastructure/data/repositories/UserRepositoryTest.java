package com.postech.saboresconectados.infrastructure.data.repositories;

import com.postech.saboresconectados.infrastructure.data.models.UserModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(value = {"/infrastructure/data/repositories/user-test-sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class UserRepositoryTest {
    @Autowired
    private UserRepository repository;

    @Test
    void shouldFindUserByLogin() {
        // Given
        final String login = "mauro.7";

        // When
        Optional<UserModel> foundUser = this.repository.findByLogin(login);

        // Then
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getLogin()).isEqualTo(login);
    }
}
