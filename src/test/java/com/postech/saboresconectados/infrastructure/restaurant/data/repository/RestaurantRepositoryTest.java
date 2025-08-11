package com.postech.saboresconectados.infrastructure.restaurant.data.repository;

import com.postech.saboresconectados.infrastructure.restaurant.data.model.RestaurantModel;
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
@Sql(value = {"/infrastructure/restaurant/repository/restaurant-test-sample.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
class RestaurantRepositoryTest {
    @Autowired
    private RestaurantRepository repository;

    @Test
    void shouldFindRestaurantByLogin() {
        // Given
        final String name = "Si Señor";

        // When
        Optional<RestaurantModel> foundRestaurant = this.repository.findByName(name);

        // Then
        assertThat(foundRestaurant).isPresent();
        assertThat(foundRestaurant.get().getName()).isEqualTo(name);
    }
}
